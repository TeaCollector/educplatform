package ru.rtstudy.educplatform.minioservice.api.responsebuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.rtstudy.educplatform.minioservice.dto.UploadResponse;
import ru.rtstudy.educplatform.minioservice.exception.NotAuthorException;
import ru.rtstudy.educplatform.minioservice.exception.NotSupportedExtension;
import ru.rtstudy.educplatform.minioservice.service.MinioService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioResponseBuilder {

    private final MinioService minioService;
    private final WebClient webClient;

    public Mono<UploadResponse> upload(String token, Mono<FilePart> files) {
        return authenticationRequest(token)
                .flatMap(authRequest -> {
                    if (authRequest) {
                        return minioService.uploadFile(files);
                    } else {
                        log.error("Not author", new NotAuthorException("You not author."));
                        return Mono.error(new NotAuthorException("You not author."));
                    }
                });
    }

    public Mono<UploadResponse> uploadStream(String token, FilePart files) {
        return authenticationRequest(token)
                .flatMap(authRequest -> {
                            if (authRequest) {
                                return minioService.putObject(files);
                            } else {
                                log.error("Not author", new NotAuthorException("You not author."));
                                return Mono.error(new NotAuthorException("You not author."));
                            }
                        }
                );
    }

    public Mono<HttpStatus> deleteFile(String token, String fileName) {
        return authenticationRequestToDeleteFile(token, fileName)
                .flatMap(isAuthor -> {
                    if (isAuthor) {
                        minioService.deleteFile(fileName);
                        return Mono.just(HttpStatus.valueOf(204));
                    } else {
                        log.error("Not author", new NotAuthorException("You not author."));
                        return Mono.error(new NotAuthorException("You not author."));
                    }
                });
    }

    public ResponseEntity<Mono<ByteArrayResource>> download(String token, String fileName) {
        if (authenticationRequest(token).block()) {
            String contentType = extractContentType(fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE,contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .body(minioService.download(fileName));
        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Mono.just(new ByteArrayResource(new byte[]{1, 0})));
        }
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

    private Mono<Boolean> authenticationRequestToDeleteFile(String token, String fileName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/check-for-delete")
                        .queryParam("file-name", fileName)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    private String extractContentType(String fileName) {
        Matcher matcher = Pattern.compile("(.*)\\.(.*)").matcher(fileName);
        if (matcher.find()) {
            String extension = matcher.group(2);
            if ("mp4".equals(extension)) {
                return "video/mp4";
            } else if ("jpg".equals(extension)) {
                return "image/jpeg";
            } else if ("mp3".equals(extension)) {
                return "audio/mpeg";
            } else if ("pdf".equals(extension)) {
                return "application/pdf";
            }
            log.error("Not supported extension: {}", extension, new NotSupportedExtension("Extension not supported."));
            throw new NotSupportedExtension("Extension not supported.");
        } else {
            log.error("Not correct input: {}", fileName, new IllegalArgumentException("Not correct input."));
            throw new IllegalStateException("Not correct input.");
        }
    }
}