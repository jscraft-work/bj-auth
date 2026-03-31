package com.bj.auth.config.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@Configuration
public class RegisteredClientDataInitializer {

    @Value("${auth.client.bj-tetris-web.redirect-uri}")
    private String redirectUri;

    @Value("${auth.client.bj-tetris-web.post-logout-redirect-uri}")
    private String postLogoutRedirectUri;

    @Bean
    public CommandLineRunner registeredClientSeeder(RegisteredClientRepository registeredClientRepository) {
        return args -> {
            RegisteredClient existingClient = registeredClientRepository.findByClientId("bj-tetris-web");
            String id = existingClient != null ? existingClient.getId() : UUID.randomUUID().toString();

            RegisteredClient bjTetrisWebClient = RegisteredClient.withId(id)
                    .clientId("bj-tetris-web")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUri(redirectUri)
                    .postLogoutRedirectUri(postLogoutRedirectUri)
                    .scope(OidcScopes.OPENID)
                    .scope(OidcScopes.PROFILE)
                    .scope("game")
                    .clientSettings(
                            ClientSettings.builder()
                                    .requireProofKey(true)
                                    .requireAuthorizationConsent(true)
                                    .build()
                    )
                    .build();

            registeredClientRepository.save(bjTetrisWebClient);
        };
    }
}
