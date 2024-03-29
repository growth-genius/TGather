package com.ysdeveloper.tgather.modules.travelgroup.dto;

import static com.ysdeveloper.tgather.modules.travelgroup.dto.FcmType.LINK;

import lombok.Data;

/**
 * FCM Message 객체
 */
@Data
public class FcmContentDto {

    private FcmType type;

    private String path;

    private String message;

    private FcmContentDto(FcmType type, String path, String message) {
        this.type = type;
        this.path = path;
        this.message = message;
    }

    /**
     * Fcm 인증 전송 메소드
     *
     * @param travelGroupId 여행그룹 고유아이디
     * @param groupName     여행그룹명
     * @return FcmContentDto Fcm 메세지 값
     */
    public static FcmContentDto createFcmContentDto(String travelGroupId, String groupName) {
        return new FcmContentDto(LINK, "/link/travel-group/" + travelGroupId, groupName + " 에서 가입 요청이 왔습니다.");
    }
}

