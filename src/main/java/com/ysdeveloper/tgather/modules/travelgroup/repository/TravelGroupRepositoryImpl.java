package com.ysdeveloper.tgather.modules.travelgroup.repository;

import static com.querydsl.core.types.Projections.constructor;
import static com.ysdeveloper.tgather.modules.travelgroup.entity.QTravelGroup.travelGroup;
import static com.ysdeveloper.tgather.modules.travelgroup.entity.QTravelGroupMember.travelGroupMember;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.ysdeveloper.tgather.infra.common.Querydsl5Support;
import com.ysdeveloper.tgather.modules.account.enums.TravelTheme;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroup;
import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroupRole;
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

    @Override
    public Optional<TravelGroup> searchByTravelGroupAndLeader(String travelGroupId, String accountId) {
        return Optional.ofNullable(selectFrom(travelGroup).innerJoin(travelGroupMember).on(travelGroupMember.travelGroup.eq(travelGroup)).fetchJoin()
            .where(travelGroup.travelGroupId.eq(travelGroupId), travelGroupMember.accountId.eq(accountId),
                travelGroupMember.travelGroupRole.eq(TravelGroupRole.LEADER)).fetchOne());
    }

    @Override
    public Optional<TravelGroup> searchByTravelGroupNameWithoutOwn(String travelGroupName, String travelGroupId) {
        return Optional.ofNullable(selectFrom(travelGroup).innerJoin(travelGroup.travelGroupMemberList, travelGroupMember).fetchJoin()
            .where(travelGroup.groupName.eq(travelGroupName), travelGroup.travelGroupId.ne(travelGroupId)).fetchOne());
    }

    @Override
    public Optional<TravelGroup> searchTravelGroupByIdWithLeader(String travelGroupId) {
        return Optional.ofNullable(selectFrom(travelGroup).innerJoin(travelGroup.travelGroupMemberList, travelGroupMember).fetchJoin()
            .where(travelGroup.travelGroupId.eq(travelGroupId), travelGroupMember.travelGroupRole.eq(TravelGroupRole.LEADER)).fetchOne());
    }

    @Override
    public List<TravelGroupSearchVo> searchTravelGroupAllByMe(String accountId) {
        return select(constructor(TravelGroupSearchVo.class, travelGroup)).leftJoin(travelGroupMember).on(travelGroupMember.travelGroup.eq(travelGroup))
            .where(travelGroupMember.accountId.eq(accountId)).from(travelGroup).fetch();
    }

    BooleanExpression containsTravelGroup(Set<TravelTheme> themes) {
        BooleanExpression contains = null;
        if (themes == null) return null;
        for (TravelTheme theme : themes) {
            if (contains == null) {
                contains = travelGroup.travelThemes.contains(theme).and(travelGroup.deleteTravelGroup.isFalse());
            } else {
                contains.and(travelGroup.travelThemes.contains(theme)).and(travelGroup.deleteTravelGroup.isFalse());
            }
        }
        return contains;
    }
}
