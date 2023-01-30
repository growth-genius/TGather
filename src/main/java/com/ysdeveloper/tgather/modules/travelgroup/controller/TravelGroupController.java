package com.ysdeveloper.tgather.modules.travelgroup.controller;

import static com.ysdeveloper.tgather.modules.utils.ApiUtil.ok;

import com.ysdeveloper.tgather.modules.common.annotation.RestBaseAnnotation;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupDTO;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupForm;
import com.ysdeveloper.tgather.modules.travelgroup.service.TravelGroupService;
import com.ysdeveloper.tgather.modules.utils.ApiUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestBaseAnnotation
@RequestMapping( "/api/travel-group" )
@RequiredArgsConstructor
public class TravelGroupController {

    private final TravelGroupService travelGroupService;

    @PostMapping
    public ApiUtil.ApiResult<TravelGroupDTO> createTravelGroup ( @RequestBody @Valid TravelGroupForm travelGroupForm ) {
        return ok( travelGroupService.createTravelGroup( travelGroupForm ) );
    }

}
