package ru.rtstudy.educplatform.minioservice.service.impl;

import io.minio.*;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.XmlParserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.rtstudy.educplatform.minioservice.dto.UploadResponse;
import ru.rtstudy.educplatform.minioservice.service.MinioService;
import ru.rtstudy.educplatform.minioservice.util.InputStreamCollector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioAsyncClient minioClient;
    @Value("${spring.minio.bucket}")
    private String bucketName;

    public Mono<UploadResponse> uploadFile(Mono<FilePart> file) {
        return file.subscribeOn(Schedulers.boundedElastic()).map(multipartFile -> {
            File temp = new File(createUUID());
            try {
                multipartFile.transferTo(temp).block();
                UploadObjectArgs uploadObjectArgs = getUploadObjectArgs(multipartFile, temp);
                ObjectWriteResponse response = minioClient.uploadObject(uploadObjectArgs).get();
                temp.delete();
                return UploadResponse.builder()
                        .objectName(response.object())
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).log();
    }


    public Mono<ByteArrayResource> download(String fileName) {
        return Mono.fromCallable(() -> {
                    InputStream response = minioClient
                            .getObject(GetObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(fileName)
                                    .build())
                            .get();
                    return new ByteArrayResource(response.readAllBytes());
                })
                .subscribeOn(Schedulers.boundedElastic());
    }


    public Mono<UploadResponse> putObject(FilePart file) {
        return file.content()
                .subscribeOn(Schedulers.boundedElastic())
                .reduce(new InputStreamCollector(),
                        (collector, dataBuffer) -> collector.collectInputStream(dataBuffer.asInputStream()))
                .map(inputStreamCollector -> {
                    try {
                        PutObjectArgs args = getPutObjectArgs(file, inputStreamCollector);
                        ObjectWriteResponse response = minioClient.putObject(args).get();
                        return UploadResponse.builder()
                                .objectName(response.object())
                                .build();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).log();
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
            return true;
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException e) {
            throw new RuntimeException(e);
        }
    }


    private UploadObjectArgs getUploadObjectArgs(FilePart multipartFile, File temp) throws IOException {
        return UploadObjectArgs.builder()
                .bucket(bucketName)
                .object(temp.getName())
                .filename(temp.getName())
                .contentType(getMediaType(multipartFile.headers()))
                .build();
    }

    private String getMediaType(HttpHeaders headers) {
        return headers.getContentType() == null ?
                MediaType.APPLICATION_OCTET_STREAM_VALUE :
                headers.getContentType().toString();
    }

    private PutObjectArgs getPutObjectArgs(FilePart file, InputStreamCollector inputStreamCollector) throws IOException {
        PutObjectArgs args = PutObjectArgs.builder()
                .object(createUUID())
                .contentType(file.headers().getContentType().toString())
                .bucket(bucketName)
                .stream(inputStreamCollector.getStream(), inputStreamCollector.getStream().available(), -1)
                .build();
        return args;
    }

    private String createUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}