package com.ysdeveloper.tgather.infra.config;

import com.ysdeveloper.tgather.infra.security.JwtAuthentication;
import com.ysdeveloper.tgather.infra.security.JwtAuthenticationToken;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginAccountAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        if (authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authenticationToken.getPrincipal();
        return Optional.of(jwtAuthentication.accountId());
    }

}

