package com.ysdeveloper.tgather.infra.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class JasyptConfigTest {

    @Autowired
    private StringEncryptor jasyptStringEncryptor;

    @Test
    void _1_encrypt_decrypt() {

        String orgText = "gzwrcviyzwccpgew";

        String encText = jasyptStringEncryptor.encrypt(orgText);

        log.error("encText:: {}", encText);

        String decrypt = jasyptStringEncryptor.decrypt(encText);

        log.error("decrypt:: {}", decrypt);

        assertEquals(orgText, decrypt);

    }
}