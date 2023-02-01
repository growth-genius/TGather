package com.ysdeveloper.tgather.modules.account.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.ysdeveloper.tgather.modules.account.entity.Account;
import lombok.Data;

@Data
public class AccountDto {

    private String username;
    private String password;
    private String nickname;
    private String email;

    AccountDto ( Account account ) {
        copyProperties( account, this );
    }

    public static AccountDto from ( Account account ) {
        return new AccountDto( account );
    }
}
