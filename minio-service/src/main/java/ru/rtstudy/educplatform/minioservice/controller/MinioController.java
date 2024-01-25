package ru.rtstudy.educplatform.minioservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.rtstudy.educplatform.minioservice.dto.UploadResponse;
import ru.rtstudy.educplatform.minioservice.exception.NotLessonAuthorException;
import ru.rtstudy.educplatform.minioservice.service.MinioService;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/object")
public class MinioController {

    private final MinioService minioService;
    private final WebClient webClient;

    @PostMapping
    public Mono<UploadResponse> upload(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                       @RequestPart(value = "files") Mono<FilePart> files) {
        authenticationRequest(token, null);
        return minioService.uploadFile(files);
    }

    @GetMapping("{file_name}")
    public ResponseEntity<Mono<ByteArrayResource>> download(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                            @PathVariable(value = "file_name") String fileName) {
        authenticationRequest(token, null);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(minioService.download(fileName));
    }

    @PostMapping("stream")
    public Mono<UploadResponse> uploadStream(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                             @RequestPart(value = "files") FilePart files) {
        authenticationRequest(token, null);
        return minioService.putObject(files);

    }

    @DeleteMapping("${file_name}")
    public ResponseEntity<Mono<HttpStatus>> deleteFile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                       @PathVariable(name = "file_name") String fileName) {
        boolean hasCredential = authenticationRequest(token, fileName);
        if (hasCredential) {
            minioService.deleteFile(fileName);
        } else {
            throw new NotLessonAuthorException("You are not lesson author.");
        }
        return ResponseEntity
                .ok(Mono.just(HttpStatus.valueOf(204)));
    }

    private boolean authenticationRequest(String token, String fileName) {
        return Boolean.TRUE.equals(webClient.get()
                .header("Authorization", token)
                .header("file-name", fileName)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }
}
