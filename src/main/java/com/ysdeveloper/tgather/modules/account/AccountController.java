package com.ysdeveloper.tgather.modules.account;

import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.form.ModifyAccountForm;
import com.ysdeveloper.tgather.modules.common.annotation.RestBaseAnnotation;
import com.ysdeveloper.tgather.modules.utils.ApiUtil;
import com.ysdeveloper.tgather.modules.utils.ApiUtil.ApiResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestBaseAnnotation
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    /**
     * 사용자 조회
     *
     * @param accountId : 사용자 식별자
     * @return 사용자 정보
     */
    @GetMapping("/{accountId}")
    public ApiResult<AccountDto> getAccount(@PathVariable String accountId) {
        return ApiUtil.success(accountService.getAccount(accountId));
    }


    /**
     * 계정 정보 수정
     *
     * @param accountId         사용자 식별자
     * @param modifyAccountForm 수정 입력 폼
     * @return 사용자 정보
     */
    @PatchMapping("/{accountId}")
    public ApiResult<AccountDto> modifyAccount(@PathVariable String accountId, ModifyAccountForm modifyAccountForm) {
        return ApiUtil.success(accountService.modifyAccount(accountId, modifyAccountForm));
    }

    /**
     * 사용자 목록 조회
     *
     * @param accountIds 조회할 사용자 아이디 목록
     * @return List<AccountDto> 조회된 사용자 목록</AccountDto>
     */
    @GetMapping
    public ApiResult<List<? extends AccountDto>> getAccounts(List<String> accountIds) {
        return ApiUtil.success(accountService.getAccounts(accountIds));
    }

}
