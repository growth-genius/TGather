package com.ysdeveloper.tgather.infra.properties;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("fcm")
public class FcmProperties {

    private List<String> scopes;

}
