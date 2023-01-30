package com.ysdeveloper.tgather.modules.aws.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties( value = "cloud.aws.region" )
public class AwsRegionProperties {

    private String staticRegion;

    public String getStatic () {
        return this.staticRegion;
    }

    public void setStatic ( String staticRegion ) {
        this.staticRegion = staticRegion;
    }

}
