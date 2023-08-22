package com.ysdeveloper.tgather.modules.travelgroup.controller;


import static com.ysdeveloper.tgather.modules.utils.ApiUtil.success;

import com.ysdeveloper.tgather.infra.security.JwtAuthentication;
import com.ysdeveloper.tgather.modules.common.annotation.RestBaseAnnotation;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupMemberDto;
import com.ysdeveloper.tgather.modules.travelgroup.service.TravelGroupMemberService;
import com.ysdeveloper.tgather.modules.utils.ApiUtil.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 여행그룹 유저 참여 관련 API
 *
 * @author joyeji
 * @since 2023.06.07
 */
@RestBaseAnnotation
@RequestMapping("/api/travel-group")
@RequiredArgsConstructor
public class TravelGroupMemberController {

    private final TravelGroupMemberService travelGroupMemberService;

    /**
     * 여행그룹 참가하기
     *
     * @param travelGroupId  여행그룹 아이디
     * @param authentication 계정정보
     * @return TravelGroupMemberDto 여행 그룹 가입
     */
    @PostMapping("/{travelGroupId}/member")
    public ApiResult<TravelGroupMemberDto> requestTravelGroupJoin(@PathVariable String travelGroupId,
        @AuthenticationPrincipal JwtAuthentication authentication) {
        return success(travelGroupMemberService.requestTravelGroupJoin(travelGroupId, authentication));
    }

    /**
     * 여행그룹 탈퇴하기
     *
     * @param travelGroupId  여행그룹 아이디
     * @param memberId       사용자 아이디
     * @param authentication 계정정보
     * @return Boolean 여행그룹 탈퇴 결과
     */
    @DeleteMapping("/{travelGroupId}/member/{memberId}")
    public ApiResult<Boolean> deleteTravelGroupMember(@PathVariable String travelGroupId, @PathVariable Long memberId,
        @AuthenticationPrincipal JwtAuthentication authentication) {
        return success(travelGroupMemberService.deleteTravelGroupMember(travelGroupId, memberId, authentication));
    }

}
