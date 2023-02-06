package com.ysdeveloper.tgather.modules.account.repository;

import com.ysdeveloper.tgather.modules.account.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional( readOnly = true )
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername ( String username );

    Optional<Account> findByNickname ( String nickname );
}
