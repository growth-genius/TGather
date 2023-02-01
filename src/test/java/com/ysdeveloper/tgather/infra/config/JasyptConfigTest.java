package com.ysdeveloper.tgather.infra.config;

import static org.assertj.core.api.Assertions.assertThat;
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
    void _0_testInit () {
        assertThat( jasyptStringEncryptor ).isNotNull();
    }

    @Test
    void _1_encrypt_decrypt () {

        String orgText = "dPwldidnfltjdrhdgkwk";

        String encText = jasyptStringEncryptor.encrypt( orgText );

        System.out.println( "encText:: " + encText );

        String decrypt = jasyptStringEncryptor.decrypt( encText );

        System.out.println( "decrypt:: {}" + decrypt );

        assertEquals( orgText, decrypt );

        log.error( jasyptStringEncryptor.encrypt( "AKIAXSCQ3NCOPIDPY2JG" ) );
        log.error( jasyptStringEncryptor.encrypt( "dnk4uTi/RxxZbIfxMm62fV0+aWdJ7E8BW7wQjQwp" ) );

    }
}