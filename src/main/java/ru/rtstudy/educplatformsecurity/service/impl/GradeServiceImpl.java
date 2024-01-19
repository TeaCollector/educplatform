package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.StudentAnswerAtAllLesson;
import ru.rtstudy.educplatformsecurity.model.Grade;
import ru.rtstudy.educplatformsecurity.repository.GradeRepository;
import ru.rtstudy.educplatformsecurity.repository.LessonRepository;
import ru.rtstudy.educplatformsecurity.service.GradeService;
import ru.rtstudy.educplatformsecurity.util.Util;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final LessonRepository lessonRepository;
    private final Util util;

    @Override
    @Transactional
    public StudentAnswerDto sendAnswer(StudentAnswerDto studentAnswerDto) {
        Grade grade = Grade.builder()
                .lesson(lessonRepository.getReferenceById(studentAnswerDto.lessonId()))
                .student(util.findUserFromContext())
                .studentAnswer(studentAnswerDto.studentAnswer())
                .build();
        gradeRepository.save(grade);

        return studentAnswerDto;
    }

    @Override
    public List<StudentAnswerAtAllLesson> findAllStudentAnswer() {
        Long id = util.findUserFromContext().getId();
        log.info("USR ID IS 3: {}?", id);
        return gradeRepository.getAllStudentAnswer(id).orElseThrow();
    }
}
