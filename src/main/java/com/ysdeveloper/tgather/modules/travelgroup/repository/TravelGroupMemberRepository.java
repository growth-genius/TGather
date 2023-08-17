package com.ysdeveloper.tgather.modules.travelgroup.repository;

import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroupMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupMemberRepository extends JpaRepository<TravelGroupMember, Long> {

    Optional<TravelGroupMember> findByAccountId(String accountId);
}
