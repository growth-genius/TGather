package com.ysdeveloper.tgather.modules.account.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountSaveForm {

    private String username;
    @NotNull( message = "별명을 입력해 주세요." )
    private String nickname;
    private String password;
    private String profileImage;

    @Min( value = 8, message = "생년월일은 8자리로 입력해 주세요. ex) 19980102" )
    private int birth;

    @NotNull( message = "이메일을 입력해 주세요." )
    @Email( message = "이메일 형식이 올바르지 않습니다." )
    private String email;

}
