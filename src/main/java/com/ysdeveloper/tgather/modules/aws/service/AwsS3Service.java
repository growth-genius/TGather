package com.ysdeveloper.tgather.modules.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ysdeveloper.tgather.modules.aws.properties.AwsS3Properties;
import com.ysdeveloper.tgather.modules.common.annotation.BaseServiceAnnotation;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class AwsS3Service {

    private final AwsS3Properties awsS3Properties;

    private final AmazonS3 amazonS3;

    public String registerImageToS3 ( MultipartFile multipartFile ) {
        ObjectMetadata objectMetadata = getObjectMetaData( multipartFile );
        String awsFileName = randomFileName( multipartFile.getOriginalFilename() );
        try ( InputStream inputStream = multipartFile.getInputStream() ) {
            amazonS3.putObject( new PutObjectRequest( awsS3Properties.getBucket(), awsFileName, inputStream, objectMetadata ) );
        } catch ( IOException e ) {
            throw new ResponseStatusException( HttpStatus.INTERNAL_SERVER_ERROR, "fail to file Upload." );
        }
        return awsFileName;

    }

    private String randomFileName ( String originalFilename ) {
        return UUID.randomUUID() + System.lineSeparator() + originalFilename;
    }

    private ObjectMetadata getObjectMetaData ( MultipartFile multipartFile ) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength( multipartFile.getSize() );
        objectMetadata.setContentType( multipartFile.getContentType() );
        return objectMetadata;
    }

}
