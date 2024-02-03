package ru.rtstudy.educplatformsecurity.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.service.CourseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DifficultResponseBuilder {

    private final CourseService courseService;

    public List<CourseShortDescriptionDto> getCoursesByDifficultId(Long id) {
        return courseService.getCoursesByDifficultId(id);
    }
}
