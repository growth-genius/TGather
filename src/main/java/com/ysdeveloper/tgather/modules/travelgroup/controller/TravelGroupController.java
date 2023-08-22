package com.ysdeveloper.tgather.modules.travelgroup.controller;

import static com.ysdeveloper.tgather.modules.utils.ApiUtil.success;

import com.ysdeveloper.tgather.infra.security.JwtAuthentication;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.common.annotation.RestBaseAnnotation;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupDto;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupModifyForm;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupSaveForm;
import com.ysdeveloper.tgather.modules.travelgroup.service.TravelGroupService;
import com.ysdeveloper.tgather.modules.utils.ApiUtil.ApiResult;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 여행그룹 CRUD Restful API
 *
 * @author joyeji
 * @since 2023.06.04
 */
@RestBaseAnnotation
@RequestMapping("/travel-group")
@RequiredArgsConstructor
public class TravelGroupController {

    private final TravelGroupService travelGroupService;

    /**
     * 여행 그룹 생성 API
     *
     * @param travelGroupSaveForm 여행 그룹 생성 폼
     * @param authentication      계정 인증
     * @return TravelGroupDto 여행그룹 생성 결과
     */
    @PostMapping
    public ApiResult<TravelGroupDto> createTravelGroup(@RequestBody @Valid TravelGroupSaveForm travelGroupSaveForm,
        @AuthenticationPrincipal JwtAuthentication authentication) {
        return success(travelGroupService.createTravelGroup(travelGroupSaveForm, authentication));
    }

    /**
     * 여행 그룹 수정 API
     *
     * @param travelGroupModifyForm 여행 그룹 수정 폼
     * @param authentication        계정 인증
     * @return TravelGroupDto 여행그룹 수정 결과
     */
    @PatchMapping("/{travelGroupId}")
    public ApiResult<TravelGroupDto> modifyTravelGroup(@PathVariable String travelGroupId, @RequestBody @Valid TravelGroupModifyForm travelGroupModifyForm,
        @AuthenticationPrincipal JwtAuthentication authentication) {
        return success(travelGroupService.modifyTravelGroup(travelGroupId, travelGroupModifyForm, authentication));
    }

    /**
     * 여행테마로 그룹 찾기 API
     *
     * @param travelThemes 여행 테마
     * @return List<TravelGroupDto> 조건에 부합한 여행그룹들
     */
    @GetMapping
    public ApiResult<List<TravelGroupDto>> findTravelGroupByTravelThemes(@RequestParam Set<TravelTheme> travelThemes) {
        return success(travelGroupService.findTravelGroupByTheme(travelThemes));
    }

    /**
     * 여행그룹 삭제 API
     *
     * @param travelGroupId  여행그룹 아이디
     * @param authentication 계정 인증
     * @return Boolean 여행그룹 삭제 결과
     */
    @DeleteMapping("/{travelGroupId}")
    public ApiResult<Boolean> deleteTravelGroup(@PathVariable String travelGroupId, @AuthenticationPrincipal JwtAuthentication authentication) {
        return success(travelGroupService.deleteTravelGroup(travelGroupId, authentication));
    }
    

}
