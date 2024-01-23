package ru.rtstudy.educplatform.minioservice.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioAsyncClient;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

@Configuration
@Slf4j
public class MinioConfig {

    @Value("${spring.minio.url}")
    private String url;

    @Value("${spring.minio.access-key}")
    private String userName;

    @Value("${spring.minio.secret-key}")
    private String password;

    @Value("${spring.minio.bucket}")
    private String bucketName;

    @Bean
    public MinioAsyncClient minioClient() {
        log.info("URL: {}. username: {}. password: {}", url, userName, password);

        MinioAsyncClient asyncClient = MinioAsyncClient.builder()
                .endpoint(url)
                .credentials(userName, password)
                .build();

        try {
            if(!asyncClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build()).get()) {
                asyncClient.makeBucket(
                        MakeBucketArgs
                                .builder()
                                .bucket(bucketName)
                                .build()
                );
            }
            return asyncClient;
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
