package ru.rtstudy.educplatform.minioservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import io.minio.GetObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.rtstudy.educplatform.minioservice.dto.UploadResponse;
import ru.rtstudy.educplatform.minioservice.service.S3Service;
import ru.rtstudy.educplatform.minioservice.util.InputStreamCollector;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static ru.rtstudy.educplatform.minioservice.util.Util.createUUID;
import static ru.rtstudy.educplatform.minioservice.util.Util.getMediaType;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;

    @Value("${amazons3.bucket-name}")
    private String bucketName;
    @Value("${s3ServiceURI}")
    private String s3ServiceURI;

    @Override
    public Mono<UploadResponse> uploadFile(Mono<FilePart> filePart) {
        return null;
    }

    @Override
    public Mono<ByteArrayResource> download(String fileName) {
        return Mono.fromCallable(() -> {
                    log.info("Downloading file: {}", fileName);
                    GetObjectRequest objectArgs = getObjectRequest(fileName);
                    InputStream response = amazonS3
                            .getObject(objectArgs)
                            .getObjectContent();
                    return new ByteArrayResource(response.readAllBytes());
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UploadResponse> putObject(FilePart file) {
        log.info("Uploading file: {}", file.filename());
        return file.content()
                .subscribeOn(Schedulers.boundedElastic())
                .reduce(new InputStreamCollector(),
                        (collector, dataBuffer) -> collector.collectInputStream(dataBuffer.asInputStream()))
                .map(inputStreamCollector -> {
                    try {
                        ObjectMetadata metadata = new ObjectMetadata();
                        metadata.setContentType(getMediaType(file.headers()));
                        String key = createUUID(file);
                        PutObjectRequest putObjectRequest = new PutObjectRequest(
                                bucketName, key, inputStreamCollector.getStream(), metadata);
                        log.debug("File will upload to: {} bucket with name: {}.", bucketName, key);
                        amazonS3.putObject(putObjectRequest);
                        return UploadResponse.builder()
                                .objectName(createReference(key))
                                .build();
                    } catch (Exception e) {
                        log.error("File wasn't upload: {}", file.filename(), new MinioException("File wasn't upload."));
                        throw new RuntimeException(e);
                    }
                }).log();
    }

    private String createReference(String key) {
        return s3ServiceURI + key;
    }

    @Override
    public boolean deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return true;
    }

    private GetObjectRequest getObjectRequest(String fileName) {
        return new GetObjectRequest(bucketName, fileName);
    }

    @Override
    public List<String> getAllObjects() {
        List<String> objectList = new ArrayList<>();
        List<S3ObjectSummary> objectName = amazonS3.listObjects(bucketName).getObjectSummaries();
        for(S3ObjectSummary name: objectName) {
            objectList.add(name.getKey());
        }
        return objectList;
    }
}