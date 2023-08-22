package com.ysdeveloper.tgather.modules.mail;

import com.ysdeveloper.tgather.modules.mail.enums.EmailSubject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailMessage {

    private String accountId;

    private String to;

    private EmailSubject mailSubject;

    private String message;
}
