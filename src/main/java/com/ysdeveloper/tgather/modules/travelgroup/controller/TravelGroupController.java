package com.ysdeveloper.tgather.modules.travelgroup.controller;

import com.ysdeveloper.tgather.modules.common.annotation.RestBaseAnnotation;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupDto;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupForm;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelSearchForm;
import com.ysdeveloper.tgather.modules.travelgroup.service.TravelGroupService;
import com.ysdeveloper.tgather.modules.utils.ApiUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestBaseAnnotation
@RequestMapping( "/api/travel-group" )
@RequiredArgsConstructor
public class TravelGroupController {

    private final TravelGroupService travelGroupService;

    @PostMapping
    public ApiUtil.ApiResult<TravelGroupDto> createTravelGroup ( @RequestBody @Valid TravelGroupForm travelGroupForm ) {
        return ApiUtil.success( travelGroupService.createTravelGroup( travelGroupForm ) );
    }

    @GetMapping
    public ApiUtil.ApiResult<List<TravelGroupDto>> findTravelGroupByTravelThemes ( @RequestBody TravelSearchForm travelSearchForm ) {
        return ApiUtil.success( travelGroupService.findTravelGroupByTheme( travelSearchForm ) );
    }

}
