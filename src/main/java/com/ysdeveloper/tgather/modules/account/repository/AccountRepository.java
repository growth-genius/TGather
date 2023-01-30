package com.ysdeveloper.tgather.modules.account.repository;

import com.ysdeveloper.tgather.modules.account.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserName ( String userName );
}
