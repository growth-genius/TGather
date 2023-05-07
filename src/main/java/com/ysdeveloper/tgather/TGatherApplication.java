package com.ysdeveloper.tgather;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ConfigurationPropertiesScan
@EncryptablePropertySource( name = "EncryptedProperties", value = "classpath:encrypted.yml" )
@EnableJpaAuditing
public class TGatherApplication {

    public static void main ( String[] args ) {
        SpringApplication.run( TGatherApplication.class, args );
    }

}
