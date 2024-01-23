package ru.rtstudy.educplatformsecurity.dto.request;


import lombok.Builder;

@Builder
public record CourseDtoRequest(
        String title,
        String description,
        short duration,
        String difficult,
        String category

) {


}
