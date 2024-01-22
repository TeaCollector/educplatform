package ru.rtstudy.educplatform.minioservice.service.impl;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.rtstudy.educplatform.minioservice.dto.UploadResponse;
import ru.rtstudy.educplatform.minioservice.service.MinioService;

import java.io.File;
import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioAsyncClient minioAsyncClient;

    @Value("${spring.minio.bucket}")
    private String bucketName;

    public Mono<UploadResponse> uploadFile(Mono<FilePart> filePart) {
        return filePart
                .subscribeOn(Schedulers.boundedElastic())
                .map(multipartFile -> {
                    long startMillis = System.currentTimeMillis();
                    File temp = new File(multipartFile.filename());
                    try {
                        log.info("Absolute path of file: {}", temp.getAbsolutePath());
                        log.info("File name is: {}", temp.getName());
                        // blocking to complete io operation
                        log.info("CAN WE READ FILE: {}", temp.canRead());
                        log.info("CAN WE WRITE FILE: {}", temp.canWrite());
                        multipartFile.transferTo(temp);

                        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                                .bucket(bucketName)
                                .object(multipartFile.filename())
                                .filename(temp.getAbsolutePath())
                                .build();

                        ObjectWriteResponse response = minioAsyncClient.uploadObject(uploadObjectArgs).get();
                        temp.deleteOnExit();
                        log.info("upload file execution time {} ms", System.currentTimeMillis() - startMillis);
                        return UploadResponse.builder()
                                .bucket(response.bucket())
                                .objectName(response.object())
                                .build();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).log();
    }


    public Mono<ByteArrayResource> download(String fileName) {
        return Mono.fromCallable(() -> {
                    InputStream response = minioAsyncClient
                            .getObject(GetObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(fileName)
                                    .build())
                            .get();
                    return new ByteArrayResource(response.readAllBytes());
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
