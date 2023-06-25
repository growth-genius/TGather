package com.ysdeveloper.tgather.modules.fcm.repository;

import com.ysdeveloper.tgather.modules.fcm.entity.FcmMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmRepository extends JpaRepository<FcmMessage, Long> {}
