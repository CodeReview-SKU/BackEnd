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

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
   private final MemberRepository memberRepository;

   private static final String GOOGLE = "google";
   private static final String KAKAO = "kakao";

   @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
       log.info("OAuth2 로그인 요청 진입");

       OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
       OAuth2User oAuth2User = delegate.loadUser(oAuth2UserRequest);

       String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
       accountProvider accountProvider = getAccoutProvider(registrationId);
       String userNameAttributeName = oAuth2UserRequest.getClientRegistration()
               .getProviderDetails()
               .getUserInfoEndpoint()
               .getUserNameAttributeName();
       Map<String, Object> attributes = oAuth2User.getAttributes();

       OAuthAttributes extractAttributes = OAuthAttributes.of(accountProvider, userNameAttributeName, attributes);
       Member createdMember = getMember(extractAttributes, accountProvider);

       return new CustomOAuth2User(
               Collections.singleton(new SimpleGrantedAuthority(String.valueOf(createdMember.getRole()))),
                       attributes,
                       extractAttributes.getNameAttributeKey(),
                       createdMember.getName(),
                       createdMember.getRole()

       );
   }

   private accountProvider getAccoutProvider(String registrationId) {
        if (KAKAO.equals(registrationId)) {
           return accountProvider.KAKAO;
        }


       return accountProvider.GOOGLE;
   }

   private Member getMember(OAuthAttributes attributes, accountProvider accountProvider) {
       Member findMember = memberRepository.findByName(attributes.getOAuth2UserInfo().getName()).orElse(null);

       if (findMember == null) {
           return saveMember(attributes, accountProvider);
       }
       return findMember;
   }

   private Member saveMember(OAuthAttributes attributes, accountProvider accountProvider) {
       Member createdMember = attributes.toEntity(accountProvider, attributes.getOAuth2UserInfo());
       return memberRepository.save(createdMember);
   }
}
