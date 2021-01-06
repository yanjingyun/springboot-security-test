package com.yjy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping(value = "get")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Object get(Authentication authentication) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
//        String token = details.getTokenValue();
        return authentication;
    }
}
