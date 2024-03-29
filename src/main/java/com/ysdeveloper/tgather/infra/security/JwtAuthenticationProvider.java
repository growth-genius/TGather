package com.ysdeveloper.tgather.infra.security;

import com.ysdeveloper.tgather.infra.advice.exceptions.NotFoundException;
import com.ysdeveloper.tgather.modules.account.AccountService;
import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.form.SignInForm;
import com.ysdeveloper.tgather.modules.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final AccountService accountService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        return processUserAuthentication(String.valueOf(authenticationToken.getPrincipal()), authenticationToken.getCredentials());
    }

    private Authentication processUserAuthentication(String principal, CredentialInfo credential) {
        try {
            AccountDto accountDto = accountService.login(new SignInForm(principal, credential.getCredential()));
            CredentialInfo credentialInfo = new CredentialInfo(accountDto.getPassword(), accountDto.getLoginType());
            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(
                new JwtAuthentication(accountDto.getId(), accountDto.getAccountId(), accountDto.getEmail(), accountDto.getNickname()), credentialInfo,
                CommonUtil.authorities(accountDto.getRoles()));
            authenticationToken.setDetails(accountDto);
            return authenticationToken;
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
