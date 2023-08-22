package com.ysdeveloper.tgather.modules.travelgroup.controller;

import static com.ysdeveloper.tgather.modules.utils.ApiUtil.success;

import com.ysdeveloper.tgather.infra.security.JwtAuthentication;
import com.ysdeveloper.tgather.modules.common.annotation.RestBaseAnnotation;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupMemberDto;
import com.ysdeveloper.tgather.modules.travelgroup.enums.GroupJoinStatus;
import com.ysdeveloper.tgather.modules.travelgroup.service.TravelGroupJoinService;
import com.ysdeveloper.tgather.modules.utils.ApiUtil.ApiResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestBaseAnnotation
@RequestMapping("/api/travel-group")
@RequiredArgsConstructor
public class TravelGroupJoinController {

    private final TravelGroupJoinService travelGroupJoinService;

    /**
     * 여행그룹에 가입신청한 유저들 전체보기
     *
     * @param travelGroupId  여행그룹 아이디
     * @param authentication 계정정보
     * @return
     */
    @GetMapping("/group-id/{travelGroupId}/status/{status}/members")
    public ApiResult<List<TravelGroupMemberDto>> getTravelGroupMembersRequest(@PathVariable String travelGroupId, @PathVariable GroupJoinStatus status,
        @AuthenticationPrincipal JwtAuthentication authentication) {
        return success(travelGroupJoinService.getTravelGroupMembersRequest(travelGroupId, status, authentication));
    }

}
