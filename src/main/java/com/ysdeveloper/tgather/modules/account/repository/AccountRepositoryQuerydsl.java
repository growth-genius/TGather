package com.ysdeveloper.tgather.modules.account.repository;

import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import java.util.List;

public interface AccountRepositoryQuerydsl {

    List<AccountDto> findAllByAccountIds(List<String> accountIds);

}
