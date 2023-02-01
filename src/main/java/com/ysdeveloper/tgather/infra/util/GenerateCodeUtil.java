package com.ysdeveloper.tgather.infra.util;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@NoArgsConstructor
public class GenerateCodeUtil {

    public static String generateCode () {
        return RandomStringUtils.randomAlphanumeric( 12 );
    }
}
