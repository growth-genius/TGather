package com.ysdeveloper.tgather.modules.travelgroup.repository;

import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupDto;
import java.util.List;
import java.util.Set;

public interface TravelGroupRepositoryQuerydsl {

    List<TravelGroupDto> searchTravelGroupAllByTravelThemes ( Set<TravelTheme> travelThemes );
}
