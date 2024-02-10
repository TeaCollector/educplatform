package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.model.Difficult;
import ru.rtstudy.educplatformsecurity.model.constant.DifficultLevel;

import java.util.List;
import java.util.Optional;

public interface DifficultService {

    Optional<Difficult> findById(Long id);

    Difficult getDifficultByDifficultName(DifficultLevel difficultLevel);
}
