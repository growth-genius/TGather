package com.ysdeveloper.tgather.modules.mail.repository;

import com.ysdeveloper.tgather.modules.mail.entity.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Email, Long> {

    Optional<Email> findByEmailAddress(String email);

}
