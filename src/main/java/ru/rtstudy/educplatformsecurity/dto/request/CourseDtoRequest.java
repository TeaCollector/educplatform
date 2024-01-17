package ru.rtstudy.educplatformsecurity.dto.request;


import org.apache.commons.lang3.builder.Diff;
import ru.rtstudy.educplatformsecurity.model.Category;
import ru.rtstudy.educplatformsecurity.model.Difficult;

public record CourseDtoRequest(
        String title,
        String description,
        short duration,
        Difficult difficult,
        Category category

) {


}
