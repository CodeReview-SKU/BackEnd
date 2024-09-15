package com.Project.BackEnd.OAuth.Service;


import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import com.Project.BackEnd.OAuth.CustomOAuth2User;
import com.Project.BackEnd.OAuth.Attributes.OAuthAttributes;
import com.Project.BackEnd.Member.Entity.Member.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.userinfo.*;

import java.util.Collections;
import java.util.Map;


/*
설명 : OAuth2 로그인 로직. OAuth2 요청을 통해서 OAuth2 객체를 정의한 것에
정보를 담는다. 이 곳에서 OAuth2를 통한 자동 로그인이 이루어진다.

 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
   private final MemberRepository memberRepository;

   @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
       log.info("OAuth2 로그인 요청 진입");

       OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
       OAuth2User oAuth2User = delegate.loadUser(oAuth2UserRequest);

       //String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
       accountProvider accountProvider = getAccoutProvider();
       String userNameAttributeName = oAuth2UserRequest.getClientRegistration()
               .getProviderDetails()
               .getUserInfoEndpoint()
               .getUserNameAttributeName();
       Map<String, Object> attributes = oAuth2User.getAttributes();
       String email = attributes.get("email").toString();
       OAuthAttributes extractAttributes = OAuthAttributes.of(accountProvider, userNameAttributeName, attributes);
       Member createdMember = getMember(extractAttributes, email, accountProvider);

       return new CustomOAuth2User(
               Collections.singleton(new SimpleGrantedAuthority(String.valueOf(createdMember.getRole()))),
                       attributes,
                       extractAttributes.getNameAttributeKey(),
                       createdMember.getName(),
                       createdMember.getEmail(),
                       createdMember.getRole()

       );
   }

   private accountProvider getAccoutProvider() {
       return accountProvider.GOOGLE;
   }

   // 멤버를 찾는 로직. 만약에 멤버가 이미 존재하는 멤버라면, 그 멤버랑 자동 연동.
   private Member getMember(OAuthAttributes attributes,String email, accountProvider accountProvider) {
       Member findMember = memberRepository.findByEmail(email).orElse(null);

       if (findMember == null) { // 존재하는 멤버가 없다면
           return saveMember(attributes,email, accountProvider); // 새로운 멤버로 등록
       }
       else { // 멤버가 존재하는데,
           if(findMember.getAccountProvider() == null) { // 기존에 ID, PW로 회원가입을 해둔 회원이면,
               findMember.setAccountProvider(accountProvider); // 소셜로그인 회원과 연동.
               return findMember;
           }
           else {
               return findMember; // 연동이 다 된 회원.
           }
       }
   }

   private Member saveMember(OAuthAttributes attributes,String email, accountProvider accountProvider) {
       Member createdMember = attributes.toEntity(accountProvider, email, attributes.getOAuth2UserInfo());
       return memberRepository.save(createdMember);
   }
}
