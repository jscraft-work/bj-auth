package com.bj.auth.controller;

import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Controller
public class ConsentController {

    private final RegisteredClientRepository registeredClientRepository;

    public ConsentController(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @GetMapping("/oauth2/consent")
    public String consent(
            Principal principal,
            Model model,
            @RequestParam("client_id") String clientId,
            @RequestParam("scope") String scope,
            @RequestParam("state") String state
    ) {
        RegisteredClient client = registeredClientRepository.findByClientId(clientId);
        String clientName = client != null ? client.getClientName() : clientId;
        Set<String> allScopes = new LinkedHashSet<>(Arrays.asList(scope.split(" ")));
        Set<String> displayScopes = new LinkedHashSet<>(allScopes);
        displayScopes.remove(OidcScopes.OPENID);

        model.addAttribute("clientId", clientId);
        model.addAttribute("clientName", clientName);
        model.addAttribute("state", state);
        model.addAttribute("scopes", displayScopes);
        model.addAttribute("principalName", principal.getName());

        return "consent";
    }
}
