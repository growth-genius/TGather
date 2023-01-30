package com.ysdeveloper.tgather.modules.aws.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties( value = "cloud.aws.credentials" )
public class AwsCredentialsProperties {

    /**
     * The access key to be used with a static provider.
     */
    private String accessKey;

    /**
     * The secret key to be used with a static provider.
     */
    private String secretKey;

}
