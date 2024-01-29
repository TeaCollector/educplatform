package ru.rtstudy.educplatform.minioservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.rtstudy.educplatform.minioservice.dto.UploadResponse;

@Tag(name = "MinIO Controller", description = "MinIO Controller API")
@RequestMapping("/object")
public interface MinioApi {

    @Operation(summary = "Загрузка файла в MinIO путём создания его копии и сохранения её в хранилище MinIO")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Успешная загрузка файла")
    )
    @PostMapping
//            (consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}) TODO: Надо проверить
    Mono<UploadResponse> upload(
            @Parameter(
                    name = "Токен авторизации",
                    description = "Токен для проверки наличия прав на загрузку файлов в хранилище MinIO",
                    required = true,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string"),
                    example = "your-token"
            )
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Parameter(
                    name = "Файл для загрузки",
                    description = "Файл, который необходимо загрузить в хранилище",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE) //TODO: в другом варианте тут было MediaType.APPLICATION_JSON_VALUE
            )
            @RequestPart(value = "files") Mono<FilePart> files);

    @Operation(summary = "Загрузка файла в MinIO путём прямого преобразования файла в поток байтов")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Успешная загрузка файла")
    )
    @PostMapping("stream")
//    (consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}) TODO: Надо проверить
    Mono<UploadResponse> uploadStream(
            @Parameter(
                    name = "Токен авторизации",
                    description = "Токен для проверки наличия прав на загрузку файлов в хранилище MinIO",
                    required = true,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string"),
                    example = "your-token"
            )
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Parameter(
                    name = "Файл для загрузки",
                    description = "Файл, который необходимо загрузить в хранилище",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE) //TODO: в другом варианте тут было MediaType.APPLICATION_JSON_VALUE
            )
            @RequestPart(value = "files") FilePart files);

    @Operation(summary = "Загрузка файла из хранилища MinIO по его UUID")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Полученный файл")
    )
    @GetMapping("{file_name}")
    ResponseEntity<Mono<ByteArrayResource>> download(
            @Parameter(
                    name = "Токен авторизации",
                    description = "Токен для проверки наличия прав на получение файлов из хранилища MinIO",
                    required = true,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string"),
                    example = "your-token"
            )
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Parameter(
                    name = "file_name",
                    description = "UUID файла",
                    required = true,
                    schema = @Schema(type = "string")
            )
            @PathVariable(value = "file_name") String fileName);

    @Operation(summary = "Удаление из хранилища MinIO по его UUID")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "204",
            description = "Успешное удаление файла")
    )
    @DeleteMapping("{file_name}")
    Mono<HttpStatus> deleteFile(
            @Parameter(
                    name = "Токен авторизации",
                    description = "Токен для проверки наличия прав на удаление файлов из хранилища MinIO",
                    required = true,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string"),
                    example = "your-token"
            )
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Parameter(
                    name = "file_name",
                    description = "UUID файла",
                    required = true,
                    schema = @Schema(type = "string")
            )
            @PathVariable(value = "file_name") String fileName);
}
