package com.ysdeveloper.tgather.infra.security;

import com.ysdeveloper.tgather.modules.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithMockJwtAuthenticationSecurityContextFactory implements WithSecurityContextFactory<WithMockJwtAuthentication> {

    private final AccountRepository accountRepository;

    @Override
    public SecurityContext createSecurityContext ( WithMockJwtAuthentication annotation ) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        return context;
    }


}
