package com.ysdeveloper.tgather.modules.fcm.entity;

import com.ysdeveloper.tgather.modules.common.CreatedEntity;
import com.ysdeveloper.tgather.modules.fcm.dto.FcmMessageDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmMessage extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 메시지 고유 식별자 */
    private String messageId;
    /** 메시지 제목 */
    private String title;
    /** 메시지 본문 */
    private String body;
    /** 이미지 */
    @Lob
    private String image;

    public static FcmMessage from(FcmMessageDto fcmMessageDto) {
        FcmMessage fcmMessage = new FcmMessage();
        fcmMessage.messageId = UUID.randomUUID().toString();
        fcmMessage.title = fcmMessageDto.getTitle();
        fcmMessage.body = fcmMessageDto.getMessage();
        fcmMessage.image = fcmMessageDto.getImage();
        return fcmMessage;
    }

}
