package com.ysdeveloper.tgather.modules.mail.enums;

import lombok.Getter;

@Getter
public enum EmailSubject {

    VALID_AUTHENTICATION_ACCOUNT(EmailType.AUTHENTICATION_ACCOUNT, "TGather 회원가입 인증 메일", "validAuthenticationMail"),
    CONFIRM_JOIN_MEMBER(EmailType.JOIN_GROUP, "TravelGroup 가입 요청 메일", "confirmJoinMemberMail");

    private final EmailType emailType;
    private final String mailSubject;
    private final String htmlFileName;

    EmailSubject(EmailType emailType, String mailSubject, String htmlFileName) {
        this.emailType = emailType;
        this.mailSubject = mailSubject;
        this.htmlFileName = htmlFileName;
    }
}
