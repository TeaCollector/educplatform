package ru.rtstudy.educplatformsecurity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.rtstudy.educplatformsecurity.model.constant.DifficultLevel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseLongDescriptionDto {

    private String title;
    private String description;
    private String category;
    private short duration;
    private DifficultLevel difficultLevel;

}
