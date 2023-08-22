package com.ysdeveloper.tgather.modules.travelgroup.repository;

import static com.ysdeveloper.tgather.modules.travelgroup.entity.QTravelGroup.travelGroup;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.ysdeveloper.tgather.infra.common.Querydsl5Support;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroup;
import com.ysdeveloper.tgather.modules.travelgroup.vo.TravelGroupSearchVo;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TravelGroupRepositoryImpl extends Querydsl5Support implements TravelGroupRepositoryQuerydsl {

    protected TravelGroupRepositoryImpl() {
        super(TravelGroup.class);
    }

    @Override
    public List<TravelGroup> searchTravelGroupAllByTravelThemes(Set<TravelTheme> travelThemes) {
        return selectFrom(travelGroup).where(containsTravelGroup(travelThemes)).fetch();
    }

    private BooleanExpression containsTravelGroup(Set<TravelTheme> travelThemes) {
        BooleanExpression contains = null;
        if (travelThemes == null) return null;
        for (TravelTheme theme : travelThemes) {
            if (contains == null) {
                contains = travelGroup.travelThemes.contains(theme).and(travelGroup.deleteTravelGroup.isFalse());
            } else {
                contains.and(travelGroup.travelThemes.contains(theme)).and(travelGroup.deleteTravelGroup.isFalse());
            }
        }
        return contains;
    }

    @Override
    public Optional<TravelGroup> searchByTravelGroupAndLeader(String travelGroupId, String accountId) {
        return Optional.empty();
    }

    @Override
    public Optional<TravelGroup> searchByTravelGroupNameWithoutOwn(String travelGroupName, String travelGroupId) {
        return Optional.empty();
    }

    @Override
    public Optional<TravelGroup> searchTravelGroupByIdWithLeader(String travelGroupId) {
        return Optional.empty();
    }

    @Override
    public List<TravelGroupSearchVo> searchTravelGroupAllByMe(String accountId) {
        return null;
    }
}
