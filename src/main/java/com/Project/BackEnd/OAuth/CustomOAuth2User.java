package com.Project.BackEnd.OAuth;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import com.Project.BackEnd.Member.Entity.Member.*;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {
    private String email;
    private String name;
    private role role;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            String name,String email, role role) {
        super(authorities, attributes, nameAttributeKey);
        this.name = name;
        this.role = role;
    }
}
