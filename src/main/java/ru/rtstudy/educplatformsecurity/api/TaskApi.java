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
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;

@Tag(name = "Task Controller", description = "Task Controller API")
@RequestMapping("api/v1/tasks")
public interface TaskApi {

    @Operation(summary = "Получить задание")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Информация о задании",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class, description = "Полученное задание"))
            })
    )
    @GetMapping("{task_id}")
    ResponseEntity<TaskDto> getTask(@PathVariable(name = "task_id")
                                    @Parameter(
                                            name = "task_id", description = "Идентификатор задания", required = true,
                                            schema = @Schema(type = "integer")
                                    )
                                    Long id);

    @Operation(summary = "Создать новое задание")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Успешное создание задания",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class, description = "Новое задание"))
            })
    )
    @PostMapping
    ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto);

    @Operation(summary = "Изменить задание")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Успешное изменение задания")
    )
    @PutMapping("{task_id}")
    ResponseEntity<TaskDto> updateTask(@PathVariable(name = "task_id")
                                       @Parameter(
                                               name = "task_id", description = "Идентификатор задания", required = true,
                                               schema = @Schema(type = "integer")
                                       )
                                       Long id,
                                       @RequestBody TaskDto taskDto);

    @Operation(summary = "Удалить задание")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "204",
            description = "Успешное удаление задания")
    )
    @DeleteMapping("{task_id}")
    ResponseEntity<HttpStatus> deleteTask(@PathVariable(name = "task_id")
                                          @Parameter(
                                                  name = "task_id", description = "Идентификатор задания", required = true,
                                                  schema = @Schema(type = "integer")
                                          )
                                          Long taskId);
}
