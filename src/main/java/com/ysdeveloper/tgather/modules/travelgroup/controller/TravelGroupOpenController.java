package com.ysdeveloper.tgather.modules.travelgroup.controller;

import static com.ysdeveloper.tgather.modules.utils.ApiUtil.success;

import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.common.annotation.RestBaseAnnotation;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupDto;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupInitDto;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupWithPageable;
import com.ysdeveloper.tgather.modules.travelgroup.service.TravelGroupService;
import com.ysdeveloper.tgather.modules.utils.ApiUtil.ApiResult;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestBaseAnnotation
@RequestMapping("/api/travel-group/open")
@RequiredArgsConstructor
public class TravelGroupOpenController {

    private final TravelGroupService travelGroupService;

    /**
     * 여행테마로 그룹 찾기 API
     *
     * @param travelThemes 여행 테마
     * @return List<TravelGroupDto> 조건에 부합한 여행그룹들
     */
    @GetMapping("/group")
    public ApiResult<List<TravelGroupDto>> findTravelGroupByTravelThemes(@RequestParam Set<TravelTheme> travelThemes) {
        return success(travelGroupService.findTravelGroupByTheme(travelThemes));
    }

    /**
     * 여행그룹 전체조회 API
     *
     * @return 여행그룹 전체 결과값
     */
    @GetMapping("/all")
    public ApiResult<TravelGroupWithPageable> findAllTravelGroups(@PageableDefault(sort = "id") Pageable pageable) {
        return success(travelGroupService.findAllTravelGroupsWithPageable(pageable));
    }

    /**
     * 여행그룹 init API
     */
    @GetMapping("/init")
    public ApiResult<TravelGroupInitDto> getTravelGroupInit() {
        return success(travelGroupService.findInitData(), "조회되었습니다.");
    }

}
