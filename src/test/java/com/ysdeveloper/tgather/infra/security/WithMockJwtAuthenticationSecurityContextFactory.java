package com.ysdeveloper.tgather.infra.security;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithMockJwtAuthenticationSecurityContextFactory implements WithSecurityContextFactory<WithMockJwtAuthentication> {

    @Override
    public SecurityContext createSecurityContext(WithMockJwtAuthentication annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(
            new JwtAuthentication(annotation.id(), annotation.accountId(), annotation.email(), annotation.nickname()), null,
            createAuthorityList(annotation.role()));
        context.setAuthentication(authentication);
        return context;
    }


}
