package ru.rtstudy.educplatformsecurity.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.dto.request.LessonDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;
//TODO: Зачем тут интерфейс, если анатации можно навесить непосредсвтенно на функции
@Tag(name = "Lesson Controller", description = "Lesson Controller API")
@RequestMapping("api/v1/lessons")
public interface LessonApi {

    @Operation(summary = "Получить описание урока")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Информация об уроке",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LessonDtoResponse.class, description = "Полученный урок"))
            })
    )
    @GetMapping("{lesson_id}")
    ResponseEntity<LessonDtoResponse> getLessonById(@PathVariable(name = "lesson_id")
                                                    @Parameter(
                                                            name = "lesson_id",
                                                            description = "Идентификатор урока",
                                                            required = true,
                                                            schema = @Schema(type = "integer")
                                                    )
                                                    Long id);

    @Operation(summary = "Создать новый урок")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Успешное создание урока",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LessonDtoResponse.class, description = "Новый урок"))
            })
    )
    @PostMapping
    ResponseEntity<LessonDtoResponse> createLesson(@RequestBody LessonDtoRequest lessonDtoRequest);

    @Operation(summary = "Изменить информацию об уроке")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Успешное изменение урока")
    )
    @PutMapping("{lesson_id}")
    ResponseEntity<LessonDtoRequest> changeLesson(@PathVariable(name = "lesson_id")
                                                  @Parameter(
                                                          name = "lesson_id",
                                                          description = "Идентификатор урока",
                                                          required = true,
                                                          schema = @Schema(type = "integer")
                                                  )
                                                  Long lessonId,
                                                  @RequestBody LessonDtoRequest lessonDtoRequest);

    @Operation(summary = "Удалить урок")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "204",
            description = "Успешное удаление урока")
    )
    @DeleteMapping("{lesson_id}")
    ResponseEntity<HttpStatusCode> deleteLesson(@PathVariable(name = "lesson_id")
                                                @Parameter(
                                                        name = "lesson_id",
                                                        description = "Идентификатор урока",
                                                        required = true,
                                                        schema = @Schema(type = "integer")
                                                )
                                                Long id);
}
