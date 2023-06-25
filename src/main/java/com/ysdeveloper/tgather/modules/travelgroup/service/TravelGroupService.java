package com.ysdeveloper.tgather.modules.travelgroup.service;

import com.ysdeveloper.tgather.infra.advice.exceptions.OmittedRequireFieldException;
import com.ysdeveloper.tgather.infra.security.JwtAuthentication;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.common.annotation.BaseServiceAnnotation;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupDto;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroup;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroupMember;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupModifyForm;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupSaveForm;
import com.ysdeveloper.tgather.modules.travelgroup.repository.TravelGroupMemberRepository;
import com.ysdeveloper.tgather.modules.travelgroup.repository.TravelGroupRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 여행그룹 CRUD 서비스
 *
 * @author joyeji
 * @since 2023.06.04
 */
@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class TravelGroupService {

    private final TravelGroupRepository travelGroupRepository;

    private final TravelGroupMemberRepository travelGroupMemberRepository;

    /**
     * 여행그룹 생성
     *
     * @param travelGroupSaveForm travelGroup 입력폼
     * @param authentication      인증정보
     * @return travelGroupDto travelGroup 생성 결과값
     */
    public TravelGroupDto createTravelGroup(TravelGroupSaveForm travelGroupSaveForm, JwtAuthentication authentication) {
        validTravelGroup(travelGroupSaveForm.getGroupName());
        TravelGroup travelGroup = TravelGroup.from(travelGroupSaveForm);
        travelGroupRepository.save(travelGroup);
        TravelGroupMember travelGroupMember = TravelGroupMember.createTravelGroupLeader(travelGroup, authentication.accountId());
        travelGroupMemberRepository.save(travelGroupMember);
        return TravelGroupDto.from(travelGroup);
    }

    /**
     * 여행그룹명 유효여부 확인
     *
     * @param travelGroupName 여행그룹명
     */
    private void validTravelGroup(String travelGroupName) {
        travelGroupRepository.findByGroupName(travelGroupName).ifPresent(m -> {
            throw new OmittedRequireFieldException("동일한 여행그룹명이 있습니다.");
        });
    }

    /**
     * 여행그룹 검색하기
     *
     * @param travelThemes travelGroup 검색 폼
     * @return TravelGroupDto travelGroup 검색 결과
     */
    public List<TravelGroupDto> findTravelGroupByTheme(Set<TravelTheme> travelThemes) {
        List<TravelGroup> travelGroups = travelGroupRepository.searchTravelGroupAllByTravelThemes(travelThemes);
        return travelGroups.stream().map(TravelGroupDto::from).toList();
    }

    /**
     * 여행그룹 정보 수정하기
     *
     * @param travelGroupModifyForm travelGroup 수정 폼
     * @param authentication        인증정보
     * @return TravelGroupDto travelGroup 수정 결과
     */
    public TravelGroupDto modifyTravelGroup(String travelGroupId, TravelGroupModifyForm travelGroupModifyForm, JwtAuthentication authentication) {
        TravelGroup travelGroup = travelGroupRepository.searchByTravelGroupAndLeader(travelGroupId, authentication.accountId())
            .orElseThrow(() -> new OmittedRequireFieldException("여행그룹명을 찾을 수 없습니다."));
        validTravelGroupWithoutOwn(travelGroupModifyForm.getGroupName(), travelGroup.getTravelGroupId());
        travelGroup.modifyTravelGroup(travelGroupModifyForm);
        return TravelGroupDto.from(travelGroup);
    }

    /**
     * 본인 그룹을 제외한 그룹명 있는지 확인
     *
     * @param travelGroupName 여행 그룹명
     * @param travelGroupId   고유 그룹 id
     */
    private void validTravelGroupWithoutOwn(String travelGroupName, String travelGroupId) {
        travelGroupRepository.searchByTravelGroupNameWithoutOwn(travelGroupName, travelGroupId).ifPresent(m -> {
            throw new OmittedRequireFieldException("동일한 여행그룹명이 있습니다.");
        });
    }

    /**
     * 여행그룹 삭제
     *
     * @param travelGroupId  여행 그룹명
     * @param authentication 인증정보
     * @return boolean 삭제 여부
     */
    public Boolean deleteTravelGroup(String travelGroupId, JwtAuthentication authentication) {
        TravelGroup travelGroup = travelGroupRepository.searchByTravelGroupAndLeader(travelGroupId, authentication.accountId())
            .orElseThrow(() -> new OmittedRequireFieldException("여행그룹명을 찾을 수 없습니다."));
        travelGroup.deleteTravelGroup();
        return true;
    }
}
