package com.ysdeveloper.tgather.infra.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention( RetentionPolicy.RUNTIME )
@WithSecurityContext( factory = WithMockJwtAuthenticationSecurityContextFactory.class )
public @interface WithMockJwtAuthentication {

    long id () default 1L;

    String email () default "leesg107@naver.com";

    String userName () default "관리자";

    String role () default "ROLE_USER";


}
