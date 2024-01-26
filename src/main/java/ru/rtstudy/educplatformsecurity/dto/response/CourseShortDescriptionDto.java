package ru.rtstudy.educplatformsecurity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Course With Full Description DTO")
public class CourseShortDescriptionDto {

    @Schema(description = "Название курса", example = "PostgreSQL для профи")
    private String title;
    @Schema(description = "Краткое описание курса",
            example = "Этот курс предназначен для разработчиков, которые уже знают основы PostgreSQL и хотят углубить свои знания.")
    private String description;

}
