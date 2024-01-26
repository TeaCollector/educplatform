package ru.rtstudy.educplatformsecurity.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Request to Create/Update Course DTO")
public record CourseDtoRequest(

        @Schema(description = "Название курса", example = "PostgreSQL для профи")
        String title,
        @Schema(description = "Краткое описание курса",
                example = "Этот курс предназначен для разработчиков, которые уже знают основы PostgreSQL и хотят углубить свои знания.")
        String description,
        @Schema(description = "Примерная продолжительность курса в часах", example = "52")
        short duration,
        @Schema(description = "Сложность данного курса", example = "MEDIUM")
        String difficult,
        @Schema(description = "Название категории, к которой относится курс", example = "BACKEND")
        String category

) {


}
