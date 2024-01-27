package ru.rtstudy.educplatformsecurity.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.dto.request.CourseDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;

@Tag(name = "Course Controller", description = "Course Controller API")
@RequestMapping("api/v1/courses")
public interface CourseApi {

    @Operation(summary = "Получить описание курса")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Информация о курсе",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CourseLongDescriptionDto.class, description = "Полученный курс"))
            })
    )
    @GetMapping("{id}")
    ResponseEntity<CourseLongDescriptionDto> getCourse(@PathVariable(name = "id")
                                                       @Parameter(
                                                               name = "id",
                                                               description = "Идентификатор курса",
                                                               required = true,
                                                               schema = @Schema(type = "integer")
                                                       )
                                                       Long id);

    @Operation(summary = "Изменить информацию о курсе")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Успешное изменение курса")
    )
    @PutMapping("{id}")
    ResponseEntity<HttpStatus> updateCourse(@PathVariable(name = "id")
                                            @Parameter(
                                                    name = "id",
                                                    description = "Идентификатор курса",
                                                    required = true,
                                                    schema = @Schema(type = "integer")
                                            )
                                            Long id,
                                            @RequestBody CourseDtoRequest courseDtoRequest);

    @Operation(summary = "Создать новый курс")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Успешное создание курса",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CourseDtoRequest.class, description = "Новый курс"))
            })
    )
    @PostMapping
    ResponseEntity<CourseDtoRequest> createCourse(@RequestBody CourseDtoRequest courseDtoRequest);

    @Operation(summary = "Удалить курс")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "204",
            description = "Успешное удаление курса")
    )
    @DeleteMapping("{id}")
    ResponseEntity<HttpStatus> deleteCourse(@PathVariable(name = "id")
                                            @Parameter(
                                                    name = "id",
                                                    description = "Идентификатор курса",
                                                    required = true,
                                                    schema = @Schema(type = "integer")
                                            )
                                            Long id);
}
