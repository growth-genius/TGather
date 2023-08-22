package com.ysdeveloper.tgather.modules.account;

import com.ysdeveloper.tgather.infra.advice.exceptions.ExpiredTokenException;
import com.ysdeveloper.tgather.infra.advice.exceptions.NoMemberException;
import com.ysdeveloper.tgather.infra.advice.exceptions.NotFoundException;
import com.ysdeveloper.tgather.infra.advice.exceptions.OmittedRequireFieldException;
import com.ysdeveloper.tgather.infra.advice.exceptions.RequiredAuthAccountException;
import com.ysdeveloper.tgather.infra.security.CredentialInfo;
import com.ysdeveloper.tgather.infra.security.Jwt;
import com.ysdeveloper.tgather.infra.security.Jwt.Claims;
import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.dto.TokenDto;
import com.ysdeveloper.tgather.modules.account.entity.Account;
import com.ysdeveloper.tgather.modules.account.enums.AccountStatus;
import com.ysdeveloper.tgather.modules.account.enums.LoginType;
import com.ysdeveloper.tgather.modules.account.form.AccountSaveForm;
import com.ysdeveloper.tgather.modules.account.form.AuthCodeForm;
import com.ysdeveloper.tgather.modules.account.form.AutoSignInForm;
import com.ysdeveloper.tgather.modules.account.form.ModifyAccountForm;
import com.ysdeveloper.tgather.modules.account.form.RenewalRefreshToken;
import com.ysdeveloper.tgather.modules.account.form.ResendAuthForm;
import com.ysdeveloper.tgather.modules.account.form.SignInForm;
import com.ysdeveloper.tgather.modules.account.repository.AccountRepository;
import com.ysdeveloper.tgather.modules.account.service.AuthService;
import com.ysdeveloper.tgather.modules.common.annotation.BaseServiceAnnotation;
import com.ysdeveloper.tgather.modules.mail.EmailMessage;
import com.ysdeveloper.tgather.modules.mail.enums.EmailSubject;
import com.ysdeveloper.tgather.modules.mail.service.EmailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final Jwt jwt;
    private final AuthService authService;
    private final EmailService emailService;

    /**
     * 회원가입
     *
     * @param accountSaveForm account 저장 폼
     * @return AccountDto account 생성 결과 Dto
     */
    public AccountDto saveAccount(AccountSaveForm accountSaveForm) {
        accountSaveForm.setPassword(passwordEncoder.encode(accountSaveForm.getPassword()));

        validateAccount(accountSaveForm);
        Account account = Account.createAccount(accountSaveForm);
        String authCode = sendSignUpConfirmEmail(account.getEmail(), account.getAccountId());
        account.updateAuthCode(authCode);
        accountRepository.save(account);
        return AccountDto.from(account);
    }

    /**
     * 이메일 인증코드 전송
     *
     * @param email 이메일
     * @return authCode 인증코드
     */
    private String sendSignUpConfirmEmail(String email, String accountId) {
        String authCode = authService.createAuthCode();
        emailService.sendEmail(
            EmailMessage.builder().accountId(accountId).to(email).message(authCode).mailSubject(EmailSubject.VALID_AUTHENTICATION_ACCOUNT).build());
        return authCode;
    }

    /**
     * 입력된 정보 확인
     *
     * @param accountSaveForm account 저장 폼
     */
    private void validateAccount(AccountSaveForm accountSaveForm) {

        if (validNickname(accountSaveForm.getNickname())) {
            throw new BadCredentialsException("이미 존재하는 닉네임입니다.");
        }

        accountRepository.findByEmail(accountSaveForm.getEmail()).ifPresent(account -> {
            throw new BadCredentialsException("이미 존재하는 이메일입니다.");
        });

    }

    /**
     * nickName 중복확인
     *
     * @param nickName 닉네임
     * @return boolean 닉네임 유효값 확인 결과
     */
    public boolean validNickname(String nickName) {
        return accountRepository.findByNickname(nickName).isPresent();
    }

    /**
     * 사용자 로그인
     *
     * @param signInForm 로그인 폼
     * @return AccountDto 계정 Dto
     */
    public AccountDto login(SignInForm signInForm) {
        String email = signInForm.getEmail();
        CredentialInfo credential = new CredentialInfo(signInForm.getPassword());
        Account account = accountRepository.findByEmailAndLoginType(email, credential.getLoginType()).orElseThrow(NoMemberException::new);

        if (account.getAccountStatus() == AccountStatus.VERIFY_EMAIL) {
            throw new RequiredAuthAccountException("이메일에 전송된 인증코드를 확인해주세요.");
        }

        account.login(passwordEncoder, credential.getCredential());
        account.afterLoginSuccess(signInForm.getFcmToken());
        return AccountDto.createByAccountAndGenerateAccessToken(account, jwt);
    }

    /**
     * 이메일 인증 확인
     *
     * @param authCodeForm 인증 코드 확인 form
     * @return AccountDto 인증 확인 AccountDto
     */
    public AccountDto validAuthCode(AuthCodeForm authCodeForm) {
        Account account = accountRepository.findByEmailAndLoginType(authCodeForm.getEmail(), LoginType.TGAHTER).orElseThrow(NoMemberException::new);

        if (!account.getAuthCode().equals(authCodeForm.getAuthCode())) {
            throw new BadCredentialsException("인증 코드가 잘못되었습니다. 다시 확인해주세요.");
        }
        account.successAuthUser();
        return AccountDto.createByAccountAndGenerateAccessToken(account, jwt);
    }

    /**
     * 토큰 갱신
     *
     * @param renewalRefreshToken 토큰 갱신 Dto
     * @return TokenDto 갱신 토큰 결과 Dto
     */
    public TokenDto renewalTokenByRefreshToken(RenewalRefreshToken renewalRefreshToken) {
        if (jwt.validateToken(renewalRefreshToken.getRefreshToken())) {
            Claims claims = jwt.verify(renewalRefreshToken.getRefreshToken());
            Account account = accountRepository.findByEmailAndLoginType(claims.getEmail(), LoginType.TGAHTER)
                .orElseThrow(() -> new NotFoundException("이메일을 찾을 수 없습니다."));
            AccountDto accountDto = AccountDto.createByAccountAndGenerateAccessToken(account, jwt);
            return TokenDto.builder().accessToken(accountDto.getAccessToken()).refreshToken(accountDto.getRefreshToken()).build();
        }
        throw new ExpiredTokenException();
    }

    public AccountDto getAccount(String accountId) {
        return AccountDto.from(accountRepository.findByAccountId(accountId).orElseThrow(NoMemberException::new));
    }

    /**
     * 메일 발송 실패시 삭제
     *
     * @param emailMessage 이메일 메시지 객체
     */
    @Transactional
    public void sendEmailFail(EmailMessage emailMessage) {
        String accountId = emailMessage.getAccountId();
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(NoMemberException::new);
        accountRepository.delete(account);
    }

    /**
     * 인증코드 재발송
     *
     * @param resendAuthForm 인증 코드 재발송 Form
     * @return CustomAccountDto
     */
    public Boolean resendAuthCode(ResendAuthForm resendAuthForm) {
        Account account = accountRepository.findByEmail(resendAuthForm.getEmail()).orElseThrow(NoMemberException::new);
        String authCode = sendSignUpConfirmEmail(resendAuthForm.getEmail(), resendAuthForm.getAccountId());
        account.updateAuthCode(authCode);
        return true;
    }

    /**
     * 사용자 정보 수정
     *
     * @param accountId         계정고유아이디
     * @param modifyAccountForm 계정수정폼
     * @return customAccountDto 수정된 accountDto
     */
    public AccountDto modifyAccount(String accountId, ModifyAccountForm modifyAccountForm) {
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(NoMemberException::new);
        account.modifyAccountInfo(modifyAccountForm);
        return AccountDto.from(account);
    }

    /**
     * 이메일 유효성 여부 확인
     *
     * @param email 이메일 인증 폼
     * @return boolean 이메일 유효성 결과
     */
    public Boolean validEmailAddress(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new OmittedRequireFieldException("이메일 형식이 올바르지 않습니다.");
        }

        return accountRepository.findByEmail(email).isPresent();
    }

    public AccountDto autoLogIn(AutoSignInForm autoSignInForm) {
        String refreshToken = autoSignInForm.getRefreshToken();
        Claims claims = jwt.verify(refreshToken);
        String accountId = claims.getAccountId();
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(NoMemberException::new);
        account.changeFcmTokenIfChanged(autoSignInForm.getFcmToken());

        return AccountDto.from(account);
    }

    public List<AccountDto> getAccounts(List<String> accountIds) {
        return accountRepository.findAllByAccountIds(accountIds);
    }

}
