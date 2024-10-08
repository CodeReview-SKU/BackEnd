package com.Project.BackEnd.OAuth.Attributes;

import com.Project.BackEnd.Member.Entity.Member;
import com.Project.BackEnd.Member.Entity.Member.*;
import com.Project.BackEnd.OAuth.UserInfo.GoogleOAuth2UserInfo;
import com.Project.BackEnd.OAuth.UserInfo.KakaoOAuth2UserInfo;
import com.Project.BackEnd.OAuth.UserInfo.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.util.Map;


@Getter
@Setter
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oAuth2UserInfo;

    @Builder
    private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }


    public static OAuthAttributes of(accountProvider accountProvider, String memberNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(memberNameAttributeName)
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public Member toEntity(accountProvider accountProvider,String email, OAuth2UserInfo oAuth2UserInfo) {
        return Member.builder()
                .accountProvider(accountProvider)
                .name(oAuth2UserInfo.getName())
                .email(email)
                .role(role.USER)
                .build();

    }
}
