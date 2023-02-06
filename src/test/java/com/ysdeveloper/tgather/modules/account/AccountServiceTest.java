package com.ysdeveloper.tgather.modules.account;

import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.form.AccountSaveForm;
import com.ysdeveloper.tgather.modules.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@RequiredArgsConstructor
class AccountServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName( value = "테스트케이스1 - 회원가입 완료" )
    void saveAccount () {
        AccountSaveForm accountSaveForm = new AccountSaveForm();
        accountSaveForm.setUsername( "yejiCho" );
        accountSaveForm.setNickname( "뿜빰뿜" );
        accountSaveForm.setBirth( 19961126 );
        accountSaveForm.setPassword( "yejiCho" );

        AccountDto accountDto = accountService.saveAccount( accountSaveForm );
        Assertions.assertEquals( accountDto.getUsername(), accountSaveForm.getUsername() );
    }
    
}