package com.ysdeveloper.tgather.infra.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ysdeveloper.tgather.modules.aws.properties.AwsCredentialsProperties;
import com.ysdeveloper.tgather.modules.aws.properties.AwsRegionProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AmazonS3Config {

    private final AwsCredentialsProperties awsCredentialsProperties;
    private final AwsRegionProperties awsRegionProperties;

    @Bean
    public BasicAWSCredentials basicAwsCredentials () {
        return new BasicAWSCredentials( awsCredentialsProperties.getAccessKey(), awsCredentialsProperties.getSecretKey() );
    }

    @Bean
    public AmazonS3 amazonS3 ( BasicAWSCredentials basicAWSCredentials ) {
        return AmazonS3ClientBuilder.standard().withRegion( awsRegionProperties.getStatic() ).withCredentials( new AWSStaticCredentialsProvider(
            basicAWSCredentials ) ).build();
    }

}
