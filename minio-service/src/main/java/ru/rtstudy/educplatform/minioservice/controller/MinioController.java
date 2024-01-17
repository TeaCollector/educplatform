package ru.rtstudy.educplatform.minioservice.controller;

import io.netty.handler.codec.http.HttpConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.rtstudy.educplatform.minioservice.dto.UploadResponse;
import ru.rtstudy.educplatform.minioservice.service.MinioService;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/object")
public class MinioController {

    private final MinioService minioService;

//    @PostMapping(path = "/",
//            produces = MimeTypeUtils.APPLICATION_JSON_VALUE,
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Mono<UploadResponse> upload(
//            @RequestPart(value = "files") Mono<FilePart> files) {
//        return minioService.uploadFile(files);
//
//    }

  @PostMapping(path = "/upload",
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<UploadResponse> uploadFile(@RequestPart(value = "file") FilePart files) {
        log.info("CONTENT TYPE: {}", files.headers().getContentType());

        return minioService.upload(files);
    }
    @GetMapping("/{file-name}")
    public ResponseEntity<Mono<ByteArrayResource>> download(@PathVariable(value = "file-name")
                                                                  String fileName) {
        return ResponseEntity
                .status(HttpStatus.PARTIAL_CONTENT)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.asMediaType(new MimeType("video/mp4")).toString())
//                .header(HttpHeaders.CONTENT_RANGE, "5253880")
//                .header(HttpHeaders.CONTENT_LENGTH, "5253880")
                .body(minioService.download(fileName));

    }
}
