package com.ysdeveloper.tgather.modules.travelgroup.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.ysdeveloper.tgather.infra.advice.exceptions.OmittedRequireFieldException;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroup;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroupRole;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TravelGroupDto {

    /** 여행그룹 아이디 */
    private String travelGroupId;

    /** 여행그룹명 */
    private String groupName;

    /** 여행그룹 설명 */
    private String description;

    /** 여행그룹 이미지 */
    private String imageUrl;

    /** 여행테마 */
    private Set<TravelTheme> travelThemes;

    private List<TravelGroupMemberDto> travelGroupMemberDtoList;

    private long limitParticipantCount = 999;

    /** 총참여자수 */
    private int totalMember;

    /** 나이 제한 범위 시작 */
    private int limitAgeRangeStart = 0;

    /** 나이 제한 범위 마지노선 */
    private int limitAgeRangeEnd = 0;

    public TravelGroupDto(TravelGroup travelGroup) {
        copyProperties(travelGroup, this);
    }

    public static TravelGroupDto from(TravelGroup travelGroup) {
        TravelGroupDto travelGroupDTO = new TravelGroupDto();
        travelGroupDTO.setGroupName(travelGroup.getGroupName());
        travelGroupDTO.setTravelThemes(travelGroup.getTravelThemes());
        travelGroupDTO.setTotalMember(travelGroup.getTravelGroupMemberList().size());
        return travelGroupDTO;
    }

    public static TravelGroupDto fromLeader(TravelGroup travelGroup) {
        Optional<TravelGroupMemberDto> optionalTravelGroupMemberDto = travelGroup.getTravelGroupMemberList().stream()
            .filter(travelGroupMember -> travelGroupMember.getTravelGroupRole() == TravelGroupRole.LEADER).map(TravelGroupMemberDto::from).findAny();

        if (optionalTravelGroupMemberDto.isEmpty()) {
            throw new OmittedRequireFieldException("여행그룹 리더를 찾을 수 없습니다.");
        }

        TravelGroupDto travelGroupDto = createTravelGroup(travelGroup);
        travelGroupDto.travelGroupMemberDtoList = List.of(optionalTravelGroupMemberDto.get());
        return travelGroupDto;
    }

    private static TravelGroupDto createTravelGroup(TravelGroup travelGroup) {
        TravelGroupDto travelGroupDto = new TravelGroupDto();
        travelGroupDto.travelGroupId = travelGroup.getTravelGroupId();
        travelGroupDto.groupName = travelGroup.getGroupName();
        travelGroupDto.travelThemes = new HashSet<>(travelGroup.getTravelThemes());
        travelGroupDto.totalMember = travelGroup.getTravelGroupMemberList().size();
        travelGroupDto.description = travelGroup.getDescription();
        travelGroupDto.imageUrl = travelGroup.getImageUrl();
        travelGroupDto.limitAgeRangeStart = travelGroup.getLimitAgeRangeStart();
        travelGroupDto.limitAgeRangeEnd = travelGroup.getLimitAgeRangeEnd();
        travelGroupDto.limitParticipantCount = travelGroup.getLimitParticipantCount();
        return travelGroupDto;
    }
}
