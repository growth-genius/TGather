package com.ysdeveloper.tgather.modules.fcm.dto;

import lombok.Data;

@Data
public class FcmMessageDto {

    private String title;
    private String message;
    private String image;
    private String token;

}
