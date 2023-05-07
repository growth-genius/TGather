package com.ysdeveloper.tgather.modules.travelgroup.service;

import com.ysdeveloper.tgather.modules.common.annotation.BaseServiceAnnotation;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupDTO;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroup;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupForm;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelSearchForm;
import com.ysdeveloper.tgather.modules.travelgroup.repository.TravelGroupRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class TravelGroupService {

    private final TravelGroupRepository travelGroupRepository;

    public TravelGroupDTO createTravelGroup ( TravelGroupForm travelGroupForm ) {

        TravelGroup travelGroup = TravelGroup.from( travelGroupForm );
        travelGroupRepository.save( travelGroup );

        return TravelGroupDTO.from( travelGroup );
    }

    public List<TravelGroupDTO> findTravelGroupByTheme ( TravelSearchForm travelSearchForm ) {
        return travelGroupRepository.searchTravelGroupAllByTravelThemes( travelSearchForm.getTravelThemes() );
    }
}
