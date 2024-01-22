package ru.rtstudy.educplatform.minioservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.rtstudy.educplatform.minioservice.dto.UploadResponse;
import ru.rtstudy.educplatform.minioservice.service.impl.MinioServiceImpl;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/object")
public class MinioController {

    private final MinioServiceImpl adapter;

    @PostMapping
    public Mono<UploadResponse> upload(@RequestPart(value = "files") Mono<FilePart> files) {
        return adapter.uploadFile(files);
    }

    @GetMapping("{file_name}")
    public ResponseEntity<Mono<ByteArrayResource>> download(@PathVariable(value = "file_name") String fileName) {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(adapter.download(fileName));
    }
}
