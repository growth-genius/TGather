package com.ysdeveloper.tgather.modules.account;

import com.ysdeveloper.tgather.infra.advice.exceptions.NotFoundException;
import com.ysdeveloper.tgather.infra.mail.EmailMessage;
import com.ysdeveloper.tgather.infra.mail.EmailService;
import com.ysdeveloper.tgather.infra.security.CredentialInfo;
import com.ysdeveloper.tgather.infra.security.Jwt;
import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.entity.Account;
import com.ysdeveloper.tgather.modules.account.enums.LoginType;
import com.ysdeveloper.tgather.modules.account.form.AccountSaveForm;
import com.ysdeveloper.tgather.modules.account.form.AuthCodeForm;
import com.ysdeveloper.tgather.modules.account.repository.AccountRepository;
import com.ysdeveloper.tgather.modules.common.annotation.BaseServiceAnnotation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final EmailService emailService;
    private final Jwt jwt;

    /**
     * 회원가입
     * @param accountSaveForm
     * @return AccountDto
     */
    public AccountDto saveAccount ( AccountSaveForm accountSaveForm ) {
        accountSaveForm.setPassword( passwordEncoder.encode( accountSaveForm.getPassword() ) );

        accountRepository.findByNickname( accountSaveForm.getNickname() ).ifPresent( account -> {
            throw new BadCredentialsException( "이미 존재하는 닉네임입니다." );
        } );
        Account account = Account.from( accountSaveForm );
        account.generateAuthCode( sendSignUpConfirmEmail( account.getEmail() ) );
        accountRepository.save( account );
        return AccountDto.from( account );
    }

    /**
     * 이메일 인증코드 전송
     * @param email
     * @return authCode
     */
    private String sendSignUpConfirmEmail ( String email ) {
        String authCode = RandomStringUtils.randomAlphanumeric( 12 );
        EmailMessage emailMessage = EmailMessage.builder().to( email ).subject( "TGather 회원가입 인증 메일" ).message( authCode ).build();
        emailService.sendEmail( emailMessage );
        return authCode;
    }

    /**
     * 사용자 로그인
     * @param email
     * @param credential
     * @return AccountDto
     */
    public AccountDto login ( String email, CredentialInfo credential ) {
        Account account = accountRepository.findByEmailAndLoginType( email, credential.getLoginType() ).orElseThrow( () -> new NotFoundException(
            "등록된 계정이 없습니다." ) );
        account.login( passwordEncoder, credential.getCredential() );
        account.afterLoginSuccess();
        return AccountDto.createByAccountAndGenerateAccessToken( account, jwt );
    }

    /**
     * 이메일 인증 확인
     * @param authCodeForm
     * @return AccountDto
     */
    public AccountDto validAuthCode ( AuthCodeForm authCodeForm ) {
        Account account = accountRepository.findByEmailAndLoginType( authCodeForm.getEmail(), LoginType.TGAHTER )
                                           .orElseThrow( () -> new NotFoundException( "등록된 계정이 없습니다." ) );

        if ( !account.getAuthCode().equals( authCodeForm.getAuthCode() ) ) {
            throw new BadCredentialsException( "인증 코드가 잘못되었습니다. 다시 확인해주세요." );
        }
        account.successAuthUser();
        return AccountDto.createByAccountAndGenerateAccessToken( account, jwt );
    }
}
