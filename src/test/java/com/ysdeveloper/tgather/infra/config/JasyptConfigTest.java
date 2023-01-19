package com.ysdeveloper.tgather.infra.config;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JasyptConfigTest {

    @Autowired
    private StringEncryptor jasyptStringEncryptor;
    @Test
    @DisplayName("암복호화 테스트")
    void encrypt_decrypt() throws Exception {
        // given
        String text = "jdbc:postgresql://115.143.85.142:5433/tgather";

        String encText = jasyptStringEncryptor.encrypt( text );
        System.err.println( "encText :: {} " + encText );
        // when
        String decrypt = jasyptStringEncryptor.decrypt( encText );
        System.err.println(  "decrypt :: {} " + decrypt );

        System.err.println(jasyptStringEncryptor.encrypt( "application" ));
        System.err.println(jasyptStringEncryptor.encrypt( "dPwldidnfltjdrhdgkwk" ));
        // then
        Assertions.assertThat( text).isEqualTo( decrypt );
    }

}