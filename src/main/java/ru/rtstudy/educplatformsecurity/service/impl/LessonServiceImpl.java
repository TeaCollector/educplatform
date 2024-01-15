package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDto;
import ru.rtstudy.educplatformsecurity.exception.LessonNotFoundException;
import ru.rtstudy.educplatformsecurity.repository.LessonRepository;
import ru.rtstudy.educplatformsecurity.service.LessonService;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public LessonDto findLessonById(Long id) {
        return lessonRepository.getLessonById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson was not found."));
    }
}
