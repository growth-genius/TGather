package com.ysdeveloper.tgather.infra.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String header;

    private String issuer;

    private String secret;

    private String tokenValidityInMilliseconds;

}
