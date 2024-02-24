package ru.rtstudy.educplatformsecurity.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
@Schema(description = "Request to Create/Update Course DTO")
public record CourseDtoRequest(

        @Schema(description = "Название курса", example = "PostgreSQL для профи")
        @NotBlank(message = "Title must be filled")
        @Size(max = 255, message = "Title must be equal or under {max} symbols")
        String title,

        @Schema(description = "Краткое описание курса",
                example = "Этот курс предназначен для разработчиков, которые уже знают основы PostgreSQL и хотят углубить свои знания.")
        @NotBlank(message = "Description must be filled")
        String description,

        @Schema(description = "Примерная продолжительность курса в часах", example = "52")
        @NotNull(message = "Duration must be filled")
        @Min(value = 1, message = "Course duration must be at least {value} hour")
        @Max(value = 999, message = "Course duration must be equal or under {value} hours")
        short duration,

        @Schema(description = "Сложность данного курса", example = "MEDIUM")
        @NotBlank(message = "Difficulty must be filled")
        @Pattern(regexp = "^(?=.*[A-Z])[A-Z_]+$", message = "Difficulty must be written with uppercase and latin symbols")
        String difficult,

        @Schema(description = "Название категории, к которой относится курс", example = "BACKEND")
        @NotBlank(message = "Category must be filled")
        @Pattern(regexp = "^(?=.*[A-Z])[A-Z_]+$", message = "Category must be written with uppercase and latin symbols")
        String category

) {


}
