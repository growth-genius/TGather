package com.ysdeveloper.tgather.modules.account;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ysdeveloper.tgather.infra.annotation.ServiceTestNoRollback;
import com.ysdeveloper.tgather.infra.container.AbstractContainerBaseTest;
import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.entity.Account;
import com.ysdeveloper.tgather.modules.account.form.AccountSaveForm;
import com.ysdeveloper.tgather.modules.account.form.AuthCodeForm;
import com.ysdeveloper.tgather.modules.account.repository.AccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

@RequiredArgsConstructor
@ServiceTestNoRollback
class AccountServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    private static final String email = "choyeji1591@gmail.com";
    private static final String nickname = "뿜빰뿜";

    @Test
    @Order(1)
    @DisplayName("테스트케이스1 - 계정생성 성공")
    void testSaveAccount() {
        // given
        AccountSaveForm accountSaveForm = new AccountSaveForm();
        accountSaveForm.setEmail(email);
        accountSaveForm.setNickname(nickname);
        accountSaveForm.setBirth(19961126);
        accountSaveForm.setPassword("yejiCho");

        // when
        AccountDto accountDto = accountService.saveAccount(accountSaveForm);
        // then
        Assertions.assertEquals(nickname, accountDto.getNickname());
    }

    @Test
    @Order(2)
    @DisplayName(value = "테스트케이스2 - 중복된 이메일로 계정 생성 시 BadCredentialsException")
    void testSaveAccountThenException() {

        // given
        AccountSaveForm accountSaveForm = new AccountSaveForm();
        accountSaveForm.setNickname(nickname);
        accountSaveForm.setBirth(19961126);
        accountSaveForm.setPassword("yejiCho");
        String email = "choyeji1591@gmail.com";
        Optional<Account> byEmail = accountRepository.findByEmail(email);
        assertTrue(byEmail.isPresent());
        accountSaveForm.setEmail(email);

        // when,then
        Assertions.assertThrows(BadCredentialsException.class, () -> {
            accountService.saveAccount(accountSaveForm);
        });
    }

    @Test
    @DisplayName(value = "테스트케이스3 - 계정 생성 성공")
    void testSuccessSaveAccount() {
        // given
        AccountSaveForm accountSaveForm = new AccountSaveForm();
        String nickname1 = "뿜뿜";
        accountSaveForm.setNickname(nickname1);
        accountSaveForm.setBirth(19961126);
        accountSaveForm.setPassword("yeji");
        accountSaveForm.setEmail("goe152@naver.com");
        // when
        AccountDto accountDto = accountService.saveAccount(accountSaveForm);
        // then
        Assertions.assertEquals(nickname1, accountDto.getNickname());
    }

    @Test
    @DisplayName("테스트케이스4 - 인증코드 확인")
    void test_case_1() {

        // given
        String email = "goe152@naver.com";
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);

        assertTrue(optionalAccount.isPresent());

        AuthCodeForm authCodeForm = new AuthCodeForm();
        authCodeForm.setEmail(email);
        authCodeForm.setAuthCode(optionalAccount.get().getAuthCode());

        // when
        AccountDto accountDto = accountService.validAuthCode(authCodeForm);

        // then
        Assertions.assertEquals(optionalAccount.get().getUuid(), accountDto.getUuid());

    }

}