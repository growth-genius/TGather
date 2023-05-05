package com.ysdeveloper.tgather.modules.travelgroup.repository;

import static com.querydsl.core.types.Projections.constructor;
import static com.ysdeveloper.tgather.modules.travelgroup.entity.QTravelGroup.travelGroup;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.common.Querydsl5Support;
import com.ysdeveloper.tgather.modules.travelgroup.dto.TravelGroupDTO;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroup;
import java.util.List;
import java.util.Set;

public class TravelGroupRepositoryImpl extends Querydsl5Support implements TravelGroupRepositoryQuerydsl {

    protected TravelGroupRepositoryImpl () {
        super( TravelGroup.class );
    }

    @Override
    public List<TravelGroupDTO> searchTravelGroupAllByTravelThemes ( Set<TravelTheme> travelThemes ) {
        return select( constructor( TravelGroupDTO.class, travelGroup ) ).from( travelGroup ).where( containsTravelGroup( travelThemes ) ).fetch();
    }

    BooleanExpression containsTravelGroup ( Set<TravelTheme> themes ) {
        BooleanExpression contains = null;
        for ( TravelTheme theme : themes ) {
            if ( contains == null ) {
                contains = travelGroup.travelThemes.contains( theme );
            } else {
                contains.and( travelGroup.travelThemes.contains( theme ) );
            }
        }
        return contains;
    }


}
