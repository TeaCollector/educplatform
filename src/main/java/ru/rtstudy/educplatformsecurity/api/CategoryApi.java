package ru.rtstudy.educplatformsecurity.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;

import java.util.List;

@Tag(name = "Category Controller", description = "Category Controller API")
@RequestMapping("api/v1/category")
public interface CategoryApi {

    @Operation(summary = "Получить список всех категорий")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Список категорий",
            content = {@Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(value = "[\"BACKEND\", \"FRONTEND\", \"DATASCIENCE\"]")
                    })
            })
    )
    @GetMapping
    ResponseEntity<List<String>> getAllAvailableCategories();

    @Operation(summary = "Список всех курсов из данной категории")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Список всех курсов",
            content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(
                            implementation = CourseShortDescriptionDto.class))
            )
            })
    )
    @GetMapping("/{id}")
    ResponseEntity<List<CourseShortDescriptionDto>> getCoursesByCategoryId(@PathVariable(name = "id")
                                                                           @Parameter(
                                                                                   name = "id",
                                                                                   description = "Идентификатор категории",
                                                                                   required = true,
                                                                                   schema = @Schema(type = "integer")
                                                                           )
                                                                           Long id);
}
