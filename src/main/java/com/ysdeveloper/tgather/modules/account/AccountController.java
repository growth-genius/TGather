package com.ysdeveloper.tgather.modules.account;

import com.ysdeveloper.tgather.infra.security.CredentialInfo;
import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.form.AccountSaveForm;
import com.ysdeveloper.tgather.modules.account.form.AuthCodeForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/account" )
public class AccountController {

    private final AccountService accountService;

    @PostMapping( "/sign-up" )
    public ResponseEntity<AccountDto> addUser ( @RequestBody @Valid AccountSaveForm accountSaveForm ) {
        return ResponseEntity.ok( accountService.saveAccount( accountSaveForm ) );
    }

    @PostMapping( "/check-email" )
    public ResponseEntity<AccountDto> authCode ( @RequestBody @Valid AuthCodeForm authCodeForm ) {
        return ResponseEntity.ok( accountService.validAuthCode( authCodeForm ) );
    }

    @PostMapping( "/login" )
    public ResponseEntity<AccountDto> login ( @RequestBody @Valid AccountSaveForm accountSaveForm ) {
        return ResponseEntity.ok( accountService.login( accountSaveForm.getEmail(), new CredentialInfo( accountSaveForm.getPassword() ) ) );
    }

}
