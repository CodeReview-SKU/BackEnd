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
        if (accountProvider == Member.accountProvider.KAKAO) {
            return ofKakao(memberNameAttributeName, attributes);
        }
        else {
            return ofGoogle(memberNameAttributeName, attributes);
        }
    }

    private static OAuthAttributes ofGoogle(String memberNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(memberNameAttributeName)
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofKakao(String memberNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(memberNameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public Member toEntity(accountProvider accountProvider, OAuth2UserInfo oAuth2UserInfo) {
        return Member.builder()
                .accountProvider(accountProvider)
                .name(oAuth2UserInfo.getName())
                .role(role.USER)
                .build();

    }
}
