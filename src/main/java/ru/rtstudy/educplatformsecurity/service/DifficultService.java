package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;

import java.util.List;

public interface DifficultService {

    List<CourseShortDescriptionDto> getCoursesByDifficult(Long id);
}
