package com.ysdeveloper.tgather.modules.travelgroup.repository;

import com.ysdeveloper.tgather.modules.travelgroup.entity.TravelGroup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupRepository extends JpaRepository<TravelGroup, Long>, TravelGroupRepositoryQuerydsl {

    Optional<TravelGroup> findByGroupName(String groupName);

}
