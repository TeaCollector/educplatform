package ru.rtstudy.educplatformsecurity.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.dto.request.MentorAnswerDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.GradeDtoResponse;
import ru.rtstudy.educplatformsecurity.dto.response.GradeStudentDtoResponse;

import java.util.List;

@Tag(name = "Mentor Controller", description = "Mentor Controller API")
@RequestMapping("/api/v1/mentors")
public interface MentorApi {

    @Operation(summary = "Получить ответы всех студентов, за все курсы, которые ты можешь проверять")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Ответы студентов",
            content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(
                            implementation = GradeDtoResponse.class))
            )
            })
    )
    @GetMapping
    ResponseEntity<List<GradeDtoResponse>> getAllAnswersForMentorCourses();

    @Operation(summary = "Получить ответы всех студентов за определённый курс")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Список всех студентов и их ответов на задание к каждому уроку курса",
            content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(
                            implementation = GradeStudentDtoResponse.class))
            )
            })
    )
    @GetMapping("/course/{course_id}")
    ResponseEntity<List<GradeStudentDtoResponse>> getAllStudentAnswersForCourse(@PathVariable(name = "course_id")
                                                                                @Parameter(
                                                                                        name = "course_id",
                                                                                        description = "Идентификатор курса",
                                                                                        required = true,
                                                                                        schema = @Schema(type = "integer")
                                                                                )
                                                                                Long id);

    @Operation(summary = "Получить ответы всех студентов за определённый урок")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Список всех студентов и их ответов на задание данному уроку",
            content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(
                            implementation = GradeStudentDtoResponse.class))
            )
            })
    )
    @GetMapping("/lessons/{lesson_id}")
    ResponseEntity<List<GradeStudentDtoResponse>> getAllStudentAnswersForLesson(@PathVariable(name = "lesson_id")
                                                                                @Parameter(
                                                                                        name = "lesson_id",
                                                                                        description = "Идентификатор урока",
                                                                                        required = true,
                                                                                        schema = @Schema(type = "integer")
                                                                                )
                                                                                Long id);

    @Operation(summary = "Оценить полученный ответ студента")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Комментарий успешно опубликован")
    )
    @PutMapping("/grades/{grade_id}")
    ResponseEntity<MentorAnswerDtoRequest> reviewStudentAnswer(@PathVariable(name = "grade_id")
                                                               @Parameter(
                                                                       name = "grade_id",
                                                                       description = "Идентификатор ответа студента",
                                                                       required = true,
                                                                       schema = @Schema(type = "integer")
                                                               )
                                                               Long id,
                                                               @RequestBody MentorAnswerDtoRequest mentorAnswerDtoRequest);

    @Operation(summary = "Изменить опубликованную оценку и комментарий ментора")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Успешное изменение ответа ментора")
    )
    @PatchMapping("/grades/{grade_id}")
    ResponseEntity<MentorAnswerDtoRequest> updateMentorAnswer(@PathVariable(name = "grade_id")
                                                              @Parameter(
                                                                      name = "grade_id",
                                                                      description = "Идентификатор ответа студента",
                                                                      required = true,
                                                                      schema = @Schema(type = "integer")
                                                              )
                                                              Long id,
                                                              @RequestBody MentorAnswerDtoRequest mentorAnswerDtoRequest);

    @Operation(summary = "Запрос на получение прав автора, получить возможность создавать свои курсы (при наличии 100 и более проверенных заданий)")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Поздравляем, вы можете создавать свои курсы")
    )
    @PostMapping("/upgrade-to-author")
    ResponseEntity<HttpStatus> upgradeMentorToAuthor();
}
