package com.ysdeveloper.tgather.modules.travelgroup.service;

import com.ysdeveloper.tgather.infra.advice.exceptions.OmittedRequireFieldException;
import com.ysdeveloper.tgather.infra.security.JwtAuthentication;
import com.ysdeveloper.tgather.modules.common.annotation.BaseServiceAnnotation;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupMemberDto;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroup;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroupMember;
import com.ysdeveloper.tgather.modules.travelgroup.enums.GroupJoinStatus;
import com.ysdeveloper.tgather.modules.travelgroup.repository.TravelGroupRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 여행그룹 참여자 관리 서비스
 *
 * @author joyeji
 * @since 2023.06.04
 */
@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class TravelGroupJoinService {

    private final TravelGroupRepository travelGroupRepository;

    /**
     * 여행그룹 가입 요청 확인
     *
     * @param travelGroupId  여행그룹 명
     * @param authentication 계정정보
     * @return List<TravelGroupMemberDto> 여행그룹 가입 신청자
     */
    public List<TravelGroupMemberDto> getTravelGroupMembersRequest(String travelGroupId, GroupJoinStatus status, JwtAuthentication authentication) {
        TravelGroup travelGroup = travelGroupRepository.searchByTravelGroupAndLeader(travelGroupId, authentication.accountId())
            .orElseThrow(() -> new OmittedRequireFieldException("여행그룹의 권한이 없습니다."));

        return switch (status) {
            case NO_APPROVED ->
                travelGroup.getTravelGroupMemberList().stream().filter(travelGroupMember -> !travelGroupMember.isApproved()).map(TravelGroupMemberDto::from)
                    .toList();
            case APPROVED -> travelGroup.getTravelGroupMemberList().stream().filter(TravelGroupMember::isApproved).map(TravelGroupMemberDto::from).toList();
            default -> travelGroup.getTravelGroupMemberList().stream().map(TravelGroupMemberDto::from).toList();
        };
    }

}
