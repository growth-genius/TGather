package com.ysdeveloper.tgather.modules.account;

import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.form.AccountSaveForm;
import com.ysdeveloper.tgather.modules.account.form.SignInForm;
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

    @PostMapping( "/add" )
    public ResponseEntity<AccountDto> addUser ( @RequestBody @Valid AccountSaveForm accountSaveForm ) {
        return ResponseEntity.ok( accountService.saveAccount( accountSaveForm ) );
    }

    @PostMapping( "/check" )
    public ResponseEntity<String> validAuthUser ( @RequestBody SignInForm signInForm ) {
        return ResponseEntity.ok( "success" );
    }
}
