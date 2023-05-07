package com.ysdeveloper.tgather.modules.travelgroup.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroup;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TravelGroupDTO {

    private String groupName;

    private Set<TravelTheme> travelThemes;

    private int totalMember;

    public TravelGroupDTO ( TravelGroup travelGroup ) {
        copyProperties( travelGroup, this );
    }

    public static TravelGroupDTO from ( TravelGroup travelGroup ) {
        TravelGroupDTO travelGroupDTO = new TravelGroupDTO();
        travelGroupDTO.setGroupName( travelGroup.getGroupName() );
        travelGroupDTO.setTravelThemes( travelGroup.getTravelThemes() );
        travelGroupDTO.setTotalMember( travelGroup.getTravelGroupMemberList().size() );
        return travelGroupDTO;
    }
}
