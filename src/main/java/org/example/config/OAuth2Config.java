package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

//@Configuration
//public class OAuth2Config {
//    @Value("${security.oauth2.client.clientId}")
//    String client_id;
//    @Value("${security.oauth2.client.clientSecret}")
//    String client_secret;
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        ClientRegistration registration = ClientRegistration
//                .withRegistrationId("google")
//                .clientId(client_id)
//                .clientSecret(client_secret)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
//                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
//                .redirectUri("http://localhost:8080/MinionsDD/callback")
//                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .clientName("Google")
//                .scope("openid", "email", "profile")
//                .build();
//
//        return new InMemoryClientRegistrationRepository(registration);
//    }
//}