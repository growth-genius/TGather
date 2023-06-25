package com.ysdeveloper.tgather.modules.fcm.controller;


import static com.ysdeveloper.tgather.modules.utils.ApiUtil.success;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ysdeveloper.tgather.modules.common.annotation.RestBaseAnnotation;
import com.ysdeveloper.tgather.modules.fcm.dto.FcmMessageDto;
import com.ysdeveloper.tgather.modules.fcm.service.FcmService;
import com.ysdeveloper.tgather.modules.utils.ApiUtil.ApiResult;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestBaseAnnotation
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FcmController {

    private final FcmService fcmService;

    @PostMapping
    public ApiResult<Boolean> pushMessage(@RequestBody FcmMessageDto fcmMessageDto) throws IOException, FirebaseMessagingException {
        return success(fcmService.pushMessage(fcmMessageDto));
    }

    @PostMapping("/list")
    public ApiResult<Boolean> pushMessages(@RequestBody List<FcmMessageDto> fcmMessageDtoList) throws IOException, FirebaseMessagingException {
        return success(fcmService.pushMessages(fcmMessageDtoList));
    }

}
