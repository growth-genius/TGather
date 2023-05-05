package com.ysdeveloper.tgather.modules.travelgroup.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ysdeveloper.tgather.infra.annotation.MockMvcTest;
import com.ysdeveloper.tgather.infra.container.AbstractContainerMvcTest;
import com.ysdeveloper.tgather.infra.security.WithMockJwtAuthentication;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelGroupForm;
import com.ysdeveloper.tgather.modules.travelgroup.form.TravelSearchForm;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

@MockMvcTest
@SpringBootTest
@WithMockJwtAuthentication
class TravelGroupControllerTest extends AbstractContainerMvcTest {

    @Test
    @DisplayName( "여행 그룹 생성 테스트" )
    void createTravelGroup () throws Exception {
        // given
        TravelGroupForm travelGroupForm = new TravelGroupForm();
        travelGroupForm.setGroupName( "국내 먹방 탐방" );
        travelGroupForm.setTravelThemes( Set.of( TravelTheme.FOOD ) );
        travelGroupForm.setStartDate( "20230505" );

        mockMvc.perform( post( "/api/travel-group" ).contentType( MediaType.APPLICATION_JSON )
                             .content( this.objectMapper.writeValueAsString( travelGroupForm ) ) ).andExpect( status().isOk() );

    }

    @Test
    @DisplayName( "여행 테마로 그룹 조회" )
    void findTravelGroupByTravelThemes () throws Exception {
        TravelSearchForm travelSearchForm = new TravelSearchForm();
        travelSearchForm.setTravelThemes( Set.of( TravelTheme.ACTIVITY ) );
        mockMvc.perform( get( "/api/travel-group" ).contentType( MediaType.APPLICATION_JSON )
                             .content( this.objectMapper.writeValueAsString( travelSearchForm ) ) ).andDo( print() ).andExpect( status().isOk() );
    }
}