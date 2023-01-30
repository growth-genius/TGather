package com.ysdeveloper.tgather.modules.account;

import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
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
    public ResponseEntity<String> addUser ( @RequestBody @Valid AccountDto accountDto ) {
        accountService.saveAccount( accountDto );
        return ResponseEntity.ok( "success" );
    }

    @PostMapping( "/auth" )
    public ResponseEntity<String> authUser ( @RequestBody AccountDto accountDto ) {
        accountService.authAccount( accountDto );
        return ResponseEntity.ok( "success" );
    }
}
