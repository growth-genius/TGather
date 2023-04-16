package com.ysdeveloper.tgather.modules.main.controller;

import com.ysdeveloper.tgather.modules.account.AccountService;
import com.ysdeveloper.tgather.modules.common.annotation.RestBaseAnnotation;
import com.ysdeveloper.tgather.modules.main.dto.TokenDto;
import com.ysdeveloper.tgather.modules.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestBaseAnnotation
@RequiredArgsConstructor
public class MainController {

    private final AccountService accountService;

    @GetMapping( "/sign/refresh-token/{refreshToken}" )
    public ApiUtil.ApiResult<TokenDto> renewalTokenByRefreshToken ( @PathVariable( "refreshToken" ) TokenDto tokenDto ) {
        return ApiUtil.success( accountService.renewalTokenByRefreshToken( tokenDto ) );
    }

}
