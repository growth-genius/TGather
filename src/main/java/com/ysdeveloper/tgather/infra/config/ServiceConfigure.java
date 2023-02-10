package com.ysdeveloper.tgather.infra.config;

import com.ysdeveloper.tgather.infra.properties.JwtProperties;
import com.ysdeveloper.tgather.infra.security.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfigure {

    @Bean
    public Jwt jwt ( JwtProperties jwtProperties ) {
        return new Jwt( jwtProperties.getIssuer(), jwtProperties.getSecret() );
    }
}
