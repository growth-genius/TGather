package com.ysdeveloper.tgather.modules.travelgroup.service;

import com.ysdeveloper.tgather.modules.common.annotation.BaseServiceAnnotation;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupDTO;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupForm;
import com.ysdeveloper.tgather.modules.travelgroup.repository.TravelGroupRepository;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class TravelGroupService {

    private final TravelGroupRepository travelGroupRepository;

    public TravelGroupDTO createTravelGroup ( TravelGroupForm travelGroupForm ) {
        return null;
    }
}
