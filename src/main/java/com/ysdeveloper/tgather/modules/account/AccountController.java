package com.ysdeveloper.tgather.modules.account;

import com.ysdeveloper.tgather.infra.security.CredentialInfo;
import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.form.AccountSaveForm;
import com.ysdeveloper.tgather.modules.account.form.AuthCodeForm;
import com.ysdeveloper.tgather.modules.common.annotation.RestBaseAnnotation;
import com.ysdeveloper.tgather.modules.utils.ApiUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestBaseAnnotation
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;

    @PostMapping( "/sign-up" )
    public ApiUtil.ApiResult<AccountDto> addUser ( @RequestBody @Valid AccountSaveForm accountSaveForm ) {
        return ApiUtil.success( accountService.saveAccount( accountSaveForm ) );
    }

    @PostMapping( "/check-email" )
    public ApiUtil.ApiResult<AccountDto> authCode ( @RequestBody @Valid AuthCodeForm authCodeForm ) {
        return ApiUtil.success( accountService.validAuthCode( authCodeForm ) );
    }

    @PostMapping( "/login" )
    public ApiUtil.ApiResult<AccountDto> login ( @RequestBody @Valid AccountSaveForm accountSaveForm ) {
        return ApiUtil.success( accountService.login( accountSaveForm.getEmail(), new CredentialInfo( accountSaveForm.getPassword() ) ) );
    }

    @GetMapping( "/check-nickname/{nickname}" )
    public ApiUtil.ApiResult<Boolean> authNickname ( @PathVariable String nickname ) {
        return ApiUtil.success( accountService.validNickname( nickname ) );
    }


}
