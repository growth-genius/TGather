package com.ysdeveloper.tgather.modules.travelgroup.form;

import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class TravelGroupForm {

    @NotEmpty( message = "여행 만남명이 누락되었습니다." )
    private String groupName;

    @NotEmpty( message = "여행 테마를 하나 이상 선택 해 주세요." )
    private Set<TravelTheme> travelThemes;

    @NotNull( message = "모임 시작일자를 선택해 주세요." )
    private String startDate;

    MultipartFile thumbnailFile;
    
}
