package ru.rtstudy.educplatform.minioservice.service;

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
import ru.rtstudy.educplatform.minioservice.util.InputStreamCollector;

import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioService {

    private final MinioAsyncClient minioAsyncClient;

    @Value("${spring.minio.bucket}")
    private String bucketName;

//    public Mono<UploadResponse> uploadFile(Mono<FilePart> filePart) {
//        return filePart
//                .subscribeOn(Schedulers.boundedElastic())
//                .map(multipartFile -> {
//                    long startMillis = System.currentTimeMillis();
//                    File temp = new File(multipartFile.filename());
//                    temp.canWrite();
//                    temp.canRead();
//                    try {
//                        log.info("Absolute path of file: {}", temp.getAbsolutePath());
//                        log.info("File name is: {}", temp.getName());
//                        // blocking to complete io operation
//                        multipartFile.transferTo(temp);
//                        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
//                                .bucket(bucketName)
//                                .object(multipartFile.filename())
//                                .filename(temp.getAbsolutePath())
//                                .build();
//
//                        ObjectWriteResponse response = minioAsyncClient.uploadObject(uploadObjectArgs).get();
//                        temp.deleteOnExit();
//                        log.info("upload file execution time {} ms", System.currentTimeMillis() - startMillis);
//                        return UploadResponse.builder()
//                                .bucket(response.bucket())
//                                .objectName(response.object())
//                                .build();
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }).log();


    public Mono<ByteArrayResource> download(String name) {
        return Mono.fromCallable(() -> {
                    InputStream response = minioAsyncClient
                            .getObject(GetObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(name)
                                    .build())
                            .get();
                    return new ByteArrayResource(response.readAllBytes());
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<UploadResponse> upload(FilePart file) {
        return file.content()
                .subscribeOn(Schedulers.boundedElastic())
                .reduce(new InputStreamCollector(),
                        (collector, dataBuffer) -> collector.collectInputStream(dataBuffer.asInputStream()))
                .map(inputStreamCollector -> {
//                    repository.save(FileMetaData.builder()
//                                    .size(file.)
//                            .build())
                    long startMillis = System.currentTimeMillis();
                    try {
                        System.out.println(file.headers().getContentType().toString());
                        PutObjectArgs args = PutObjectArgs.builder()
                                .object(file.filename())
                                .contentType(file.headers().getContentType().toString())
                                .bucket(bucketName)
                                .stream(inputStreamCollector.getStream(), inputStreamCollector.getStream().available(), -1)
                                .build();
                        ObjectWriteResponse response = minioAsyncClient.putObject(args).get();
                        log.info("upload file execution time {} ms", System.currentTimeMillis() - startMillis);
                        return UploadResponse.builder()
                                .bucket(response.bucket()).objectName(response.object()).build();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).log();
    }

    public InputStream getInputStream(UUID uuid, long offset, long length) throws Exception {
        return minioAsyncClient.getObject(
                        GetObjectArgs
                                .builder()
                                .bucket("sample")
                                .offset(offset)
                                .length(length)
                                .object(uuid.toString())
                                .build())
                .get();
    }


}
