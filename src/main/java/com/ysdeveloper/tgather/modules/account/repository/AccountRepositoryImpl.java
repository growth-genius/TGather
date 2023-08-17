package com.ysdeveloper.tgather.modules.account.repository;

import static com.querydsl.core.types.Projections.constructor;
import static com.ysdeveloper.tgather.modules.account.entity.QAccount.account;

import com.ysdeveloper.tgather.infra.common.Querydsl5Support;
import com.ysdeveloper.tgather.modules.account.dto.AccountDto;
import com.ysdeveloper.tgather.modules.account.entity.Account;
import java.util.List;

public class AccountRepositoryImpl extends Querydsl5Support implements AccountRepositoryQuerydsl {

    protected AccountRepositoryImpl() {
        super(Account.class);
    }

    @Override
    public List<AccountDto> findAllByAccountIds(List<String> accountIds) {
        return select(constructor(AccountDto.class, account)).from(account).where(account.accountId.in(accountIds)).fetch();
    }

}
