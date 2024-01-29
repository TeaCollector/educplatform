package ru.rtstudy.educplatformsecurity.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;

@Tag(name = "User Controller", description = "User Controller API")
@RequestMapping("api/v1/users")
public interface UserApi {

    @Operation(summary = "Получить информацию о пользователе")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Информация о пользователе",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDtoResponse.class))
            })
    )
    @GetMapping
    ResponseEntity<UserDtoResponse> getUserInfo();
}
