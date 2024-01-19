package ru.rtstudy.educplatformsecurity.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import ru.rtstudy.educplatformsecurity.model.Category;
import ru.rtstudy.educplatformsecurity.model.Difficult;

@Builder
public record CourseDtoRequest(
        String title,
        String description,
        short duration,
        String difficult,
        String category

) {


}
