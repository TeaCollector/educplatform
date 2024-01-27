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
import ru.rtstudy.educplatform.minioservice.exception.NotAuthenticatedException;
import ru.rtstudy.educplatform.minioservice.exception.NotAuthorException;
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
        return authenticationRequest(token).flatMap(isAuthor -> {
            if (isAuthor) {
                return minioService.uploadFile(files);
            } else {
                return Mono.error(new NotAuthorException("You not author."));
            }
        });
    }

    @PostMapping("stream")
    public Mono<UploadResponse> uploadStream(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                             @RequestPart(value = "files") FilePart files) {
        boolean isAuthor = authenticationRequest(token).block();
        if (isAuthor) {
            return minioService.putObject(files);
        } else {
            try {
                throw new NotAuthorException("You not author.");
            } catch (NotAuthorException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping("{file_name}")
    public ResponseEntity<Mono<ByteArrayResource>> download(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                            @PathVariable(value = "file_name") String fileName) {
        boolean isAuthenticated = authenticationRequest(token).block();
        log.info("Is Authenticated: {}", isAuthenticated);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(minioService.download(fileName));
    }

    @DeleteMapping("{file_name}")
    public ResponseEntity<Mono<HttpStatus>> deleteFile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                       @PathVariable(value = "file_name") String fileName) {
        boolean hasCredential = authenticationRequestToDeleteFile(token, fileName);
        if (hasCredential) {
            minioService.deleteFile(fileName);
        } else {
            throw new NotLessonAuthorException("You are not lesson author.");
        }
        return ResponseEntity
                .ok(Mono.just(HttpStatus.valueOf(204)));
    }

    private Mono<Boolean> authenticationRequest(String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/check")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    private Boolean authenticationRequestToDeleteFile(String token, String fileName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/check-for-delete")
                        .queryParam("file-name", fileName)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, token)
//                .header("file-name", fileName)
//                .exchangeToMono(response -> {
//                    log.info("RESPONSE: {}", response);
//                    if (response.statusCode().equals(HttpStatus.OK)) {
//                        return response.bodyToMono(Boolean.class);
//                    }
//                    throw new NotAuthenticatedException("You are not authenticated.");
//                })
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}
