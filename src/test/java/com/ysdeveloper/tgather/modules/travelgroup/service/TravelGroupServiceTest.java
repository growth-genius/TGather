package com.ysdeveloper.tgather.modules.travelgroup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ysdeveloper.tgather.infra.annotation.ServiceTest;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupDto;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroup;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupForm;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelSearchForm;
import com.ysdeveloper.tgather.modules.travelgroup.repository.TravelGroupRepository;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class TravelGroupServiceTest {

    @Autowired
    private TravelGroupService travelGroupService;

    @Autowired
    private TravelGroupRepository travelGroupRepository;

    @Test
    @DisplayName( "여행 그룹 생성 확인" )
    void createTravelGroup () {
        TravelGroupForm travelGroupForm = new TravelGroupForm();
        travelGroupForm.setGroupName( "일본" );
        travelGroupForm.setTravelThemes( Set.of( TravelTheme.FOOD ) );

        TravelGroupDto result = travelGroupService.createTravelGroup( travelGroupForm );
        TravelGroup travelGroup = travelGroupRepository.findByGroupName( "일본" );

        assertEquals( travelGroup.getGroupName(), result.getGroupName() );

    }

    @Test
    @DisplayName( "여행 테마 조회" )
    void test_case_1 () {
        TravelGroupForm travelGroupForm = new TravelGroupForm();
        travelGroupForm.setGroupName( "일본" );
        travelGroupForm.setTravelThemes( Set.of( TravelTheme.FOOD ) );

        travelGroupService.createTravelGroup( travelGroupForm );

        TravelSearchForm travelSearchForm = new TravelSearchForm();
        travelSearchForm.setTravelThemes( Set.of( TravelTheme.FOOD ) );

        List<TravelGroupDto> result = travelGroupService.findTravelGroupByTheme( travelSearchForm );

        assertEquals( 1, result.size() );
    }


}