package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.ChangeStudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.AllStudentAnswers;
import ru.rtstudy.educplatformsecurity.exception.AnswersNotFoundException;
import ru.rtstudy.educplatformsecurity.model.Grade;
import ru.rtstudy.educplatformsecurity.model.User;
import ru.rtstudy.educplatformsecurity.repository.GradeRepository;
import ru.rtstudy.educplatformsecurity.repository.LessonRepository;
import ru.rtstudy.educplatformsecurity.service.GradeService;
import ru.rtstudy.educplatformsecurity.util.Util;

import java.util.List;

@RequiredArgsConstructor
@Service
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
    @Transactional
    public List<AllStudentAnswers> findAllStudentAnswer() {
        Long id = util.findUserFromContext().getId();
        return gradeRepository.getAllStudentAnswer(id)
                .orElseThrow(() -> new AnswersNotFoundException("Answers not found."));
    }

    @Override
    @Transactional
    public List<AllStudentAnswers> findAllStudentsAnswerForCourse(Long courseId) {
        Long userId = util.findUserFromContext().getId();
        return gradeRepository.findAllStudentsAnswerForCourse(courseId, userId)
                .orElseThrow(() -> new AnswersNotFoundException("Answers not found."));
    }


    // TODO: 19.01.2024 Какой ответ студента будем исправлять? Последний к выбранному уроку? Если он еще не проверен
    @Override
    @Transactional
    public ChangeStudentAnswerDto changeAnswer(Long id, ChangeStudentAnswerDto studentsAnswerDto) {
        Long studentId = util.findUserFromContext().getId();
        gradeRepository.changeAnswer(id, studentsAnswerDto.studentAnswer(), studentId);
        return studentsAnswerDto;
    }
}
