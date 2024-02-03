package ru.rtstudy.educplatform.minioservice.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.rtstudy.educplatform.minioservice.api.MinioApi;
import ru.rtstudy.educplatform.minioservice.api.responsebuilder.MinioResponseBuilder;
import ru.rtstudy.educplatform.minioservice.dto.UploadResponse;
import ru.rtstudy.educplatform.minioservice.exception.NotAuthorException;
import ru.rtstudy.educplatform.minioservice.service.MinioService;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MinioController implements MinioApi {

    private final MinioResponseBuilder responseBuilder;

    @Override
    public Mono<UploadResponse> upload(String token, Mono<FilePart> files) {
        return responseBuilder.upload(token, files);
    }

    @Override
    public Mono<UploadResponse> uploadStream(String token, FilePart files) {
        return responseBuilder.uploadStream(token, files);
    }

    @Override
    public ResponseEntity<Mono<ByteArrayResource>> download(String token, String fileName) {
        return responseBuilder.download(token, fileName);
    }

    @Override
    public Mono<HttpStatus> deleteFile(String token, String fileName) {
        return responseBuilder.deleteFile(token, fileName);
    }
}
