package com.ysdeveloper.tgather.modules.aws.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties( "cloud.aws.s3" )
public class AwsS3Properties {

    private String bucket;

}
