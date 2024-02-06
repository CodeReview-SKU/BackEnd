package com.Project.BackEnd.Security;


import com.Project.BackEnd.Jwt.Filter.JwtFilter;
import com.Project.BackEnd.Jwt.Service.JwtService;
import com.Project.BackEnd.Login.Handler.LoginFailureHandler;
import com.Project.BackEnd.Login.Handler.LoginSuccessHandler;
import com.Project.BackEnd.Login.Service.LoginService;
import com.Project.BackEnd.Member.Repository.MemberRepository;
import com.Project.BackEnd.OAuth.Handler.OAuth2LoginFailureHandler;
import com.Project.BackEnd.OAuth.Handler.OAuth2LoginSuccessHandler;
import com.Project.BackEnd.OAuth.Service.CustomOAuth2UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final LoginService loginService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))


                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/","/css/**","/images/**","/js/**","/favicon.ico","/h2-console/**","/static/**").permitAll()
                                .requestMatchers("/signup").permitAll()
                                .anyRequest().authenticated()
                )

                .oauth2Login(handler ->
                        handler.successHandler(oAuth2LoginSuccessHandler)
                                .failureHandler(oAuth2LoginFailureHandler)
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.userService(customOAuth2UserService))
                );

        http.addFilterBefore(jwtFilter(), LogoutFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager () {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(loginService);

        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, memberRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public JwtFilter jwtFilter() {
        JwtFilter jwtFilter = new JwtFilter(jwtService, memberRepository);
        return jwtFilter;
    }
}
