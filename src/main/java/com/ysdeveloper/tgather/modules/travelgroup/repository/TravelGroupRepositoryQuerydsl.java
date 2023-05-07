package com.ysdeveloper.tgather.modules.travelgroup.repository;

import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupDTO;
import java.util.List;
import java.util.Set;

public interface TravelGroupRepositoryQuerydsl {

    List<TravelGroupDTO> searchTravelGroupAllByTravelThemes ( Set<TravelTheme> travelThemes );
}
