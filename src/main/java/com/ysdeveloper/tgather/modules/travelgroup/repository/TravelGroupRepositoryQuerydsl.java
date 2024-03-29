package com.ysdeveloper.tgather.modules.travelgroup.repository;

import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroup;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TravelGroupRepositoryQuerydsl {

    /**
     * 여행테마가 같은 여행그룹 전체 검색
     *
     * @param travelThemes 여행테마들
     * @return List<TravelGroupDto> 검색된 여행그룹 결과
     */
    List<TravelGroup> searchTravelGroupAllByTravelThemes(Set<TravelTheme> travelThemes);

    /**
     * 본인이 생성한 travelGroup 검색
     *
     * @param travelGroupId 그룹명
     * @param accountId     계정아이디
     * @return Optional<TravelGroup> 검색된 여행그룹 결과
     */
    Optional<TravelGroup> searchByTravelGroupAndLeader(String travelGroupId, String accountId);

    /**
     * 본인 그룹을 제외한 그룹명 있는지 확인
     *
     * @param travelGroupName 여행그룹명
     * @param travelGroupId   본인 여행그룹 id
     * @return Optional<TravelGroup> 검색된 여행그룹 결과
     */
    Optional<TravelGroup> searchByTravelGroupNameWithoutOwn(String travelGroupName, String travelGroupId);

    /**
     * 그룹명 검색후 Leader 찾기
     *
     * @param travelGroupId 여행그룹명
     * @return Optional<TravelGroup> 여행그룹
     */
    Optional<TravelGroup> searchTravelGroupByIdWithLeader(String travelGroupId);

}
