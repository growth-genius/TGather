package com.ysdeveloper.tgather.modules.account;

import com.ysdeveloper.tgather.infra.advice.exceptions.ExpiredTokenException;
import com.ysdeveloper.tgather.infra.mail.EmailMessage;
import com.ysdeveloper.tgather.infra.mail.EmailService;
import com.ysdeveloper.tgather.infra.util.GenerateCodeUtil;
import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.entity.Account;
import com.ysdeveloper.tgather.modules.account.form.AccountSaveForm;
import com.ysdeveloper.tgather.modules.account.form.AuthOtpCodeForm;
import com.ysdeveloper.tgather.modules.account.form.SignInForm;
import com.ysdeveloper.tgather.modules.account.repository.AccountRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final EmailService emailService;

    public AccountDto saveAccount ( AccountSaveForm accountDto ) {
        accountDto.setPassword( passwordEncoder.encode( accountDto.getPassword() ) );

        accountRepository.findByNickname( accountDto.getNickname() ).ifPresent( account -> {
            throw new BadCredentialsException( "이미 존재하는 닉네임입니다." );
        } );

        Account account = Account.from( accountDto );
        accountRepository.save( account );
        return AccountDto.from( account );
    }

    public void authAccount ( SignInForm signInForm ) {
        Account account = accountRepository.findByUsername( signInForm.getUsername() ).orElseThrow( () -> new RuntimeException(
            "해당하는 아이디가 없습니다. 다시 입력해주세요." ) );
        if ( passwordEncoder.matches( signInForm.getPassword(), account.getPassword() ) ) {

            EmailMessage emailMessage = EmailMessage.builder()
                                                    .subject( "Tgahter 로그인 인증 otp 생성" )
                                                    .to( account.getEmail() )
                                                    .message( renewOtp( account ) )
                                                    .build();
            emailService.sendEmail( emailMessage );
        } else {
            throw new BadCredentialsException( "password가 잘못 입력되었습니다." );
        }
    }

    private String renewOtp ( Account account ) {
        String code = GenerateCodeUtil.generateCode();
        account.changeOtpCode( code );
        return code;
    }

    public void validAuthUser ( AuthOtpCodeForm authOtpCodeForm ) {
        // jwt 인증
        validOtpCode( authOtpCodeForm );
    }

    public void validOtpCode ( AuthOtpCodeForm authOtpCodeForm ) {
        // otp 확인
        Account account = accountRepository.findByUsername( authOtpCodeForm.getUsername() ).orElseThrow( () -> new RuntimeException(
            "해당하는 아이디가 없습니다. 다시 입력해주세요." ) );

        LocalDateTime now = LocalDateTime.now();
        if ( now.isAfter( account.getOtpCodeModifiedAt().plusMinutes( 3 ) ) ) {
            throw new ExpiredTokenException( "인증번호가 만료되었습니다. 다시 인증하세요." );
        }

    }
}
