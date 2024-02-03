package ru.rtstudy.educplatformsecurity.api;

 import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rtstudy.educplatformsecurity.dto.request.UserUpdateDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;
//TODO: Зачем тут интерфейс, если анатации можно навесить непосредсвтенно на функции
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


    @Operation(summary = "Изменить информацию о пользователе")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "202",
            description = "Данные пользователя изменены",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDtoResponse.class))
            })
    )
    @PutMapping
    ResponseEntity<UserUpdateDto> updateUser(@RequestBody UserUpdateDto userDtoResponse);
}
