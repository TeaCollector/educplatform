package ru.rtstudy.educplatformsecurity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Category DTO")
public record CategoryDto(
        @Schema(description = "Идентификатор категории", example = "1")
        Long id,
        @Schema(description = "Название категории", example = "BACKEND")
        String title) {
}
