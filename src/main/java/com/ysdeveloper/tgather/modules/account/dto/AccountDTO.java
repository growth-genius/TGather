package com.ysdeveloper.tgather.modules.account.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AccountDto {

    private String userName;
    private String password;
    @Email
    private String email;

}
