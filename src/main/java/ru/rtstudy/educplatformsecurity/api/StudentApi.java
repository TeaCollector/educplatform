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
import ru.rtstudy.educplatformsecurity.dto.ChangeStudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.AllStudentAnswers;

import java.util.List;
//TODO: Зачем тут интерфейс, если анатации можно навесить непосредсвтенно на функции
@Tag(name = "Student Controller", description = "Student Controller API")
@RequestMapping("api/v1/students")
public interface StudentApi {

    @Operation(summary = "Поступить на выбранный курс")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Поздравляем, вы зачислены!")
    )
    @PostMapping("start/{course_id}")
    ResponseEntity<HttpStatus> enterOnCourse(@PathVariable(name = "course_id")
                                             @Parameter(
                                                     name = "course_id",
                                                     description = "Идентификатор курса, на который хочет поступить студент",
                                                     required = true,
                                                     schema = @Schema(type = "integer")
                                             )
                                             Long courseId);

    @Operation(summary = "Закончить выбранный курс")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Курс окончен")
    )
    @PostMapping("finish-course/{course_id}")
    ResponseEntity<HttpStatus> finishCourse(@PathVariable(name = "course_id")
                                            @Parameter(
                                                    name = "course_id",
                                                    description = "Идентификатор курса, который студент хочет закончить",
                                                    required = true,
                                                    schema = @Schema(type = "integer")
                                            )
                                            Long courseId);

    @Operation(summary = "Отправить ответ на указанной урок для проверки")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Задание отправлено на проверку")
    )
    @PostMapping
    ResponseEntity<StudentAnswerDto> sendAnswer(@RequestBody StudentAnswerDto studentAnswerDto);

    @Operation(summary = "Получить все оценки и все свои ответы с комментариями преподавателя на сданные задания")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Список ответов пользователя на задания с комментарием и оценкой от ментора ",
            content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(
                            implementation = AllStudentAnswers.class))
            )
            })
    )
    @GetMapping
    ResponseEntity<List<AllStudentAnswers>> receiveAllStudentsAnswer();

    @Operation(summary = "Получить все оценки и все свои ответы с комментариями преподавателя за каждый урок указанного курса")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Список ответов пользователя на задания с комментарием и оценкой от ментора ",
            content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(
                            implementation = AllStudentAnswers.class))
            )
            })
    )
    @GetMapping("course/{course_id}")
    ResponseEntity<List<AllStudentAnswers>> receiveAllStudentsAnswerForCourse(@PathVariable(name = "course_id")
                                                                              @Parameter(
                                                                                      name = "course_id",
                                                                                      description = "Идентификатор курса, за который получить всю информацию",
                                                                                      required = true,
                                                                                      schema = @Schema(type = "integer")
                                                                              )
                                                                              Long courseId);

    @Operation(summary = "Изменить свой ответ, только если он не был проверен ментором")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Успешное изменение ответа")
    )
    @PutMapping("lesson/{id}")
    ResponseEntity<ChangeStudentAnswerDto> changeAnswer(@PathVariable(name = "id")
                                                        @Parameter(
                                                                name = "id",
                                                                description = "Идентификатор урока, к которому надо изменить ответ",
                                                                required = true,
                                                                schema = @Schema(type = "integer")
                                                        )
                                                        Long id,
                                                        @RequestBody ChangeStudentAnswerDto studentsAnswerDto);

    @Operation(summary = "Запрос на получение прав ментора на курс, получить возможность проверять ответы пользователей (при наличии среднего балла 8.0 за задания курса)")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Поздравляем, вы стали ментором")
    )
    @PostMapping("upgrade-to-mentor/{course_id}")
    ResponseEntity<HttpStatus> upgradeToMentor(@PathVariable(name = "course_id")
                                               @Parameter(
                                                       name = "course_id",
                                                       description = "Идентификатор курса, для которого собираемся стать ментором",
                                                       required = true,
                                                       schema = @Schema(type = "integer")
                                               )
                                               Long courseId);
}
