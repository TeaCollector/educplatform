package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.StudentAnswerAtAllLesson;

import java.util.List;

public interface GradeService {

    StudentAnswerDto sendAnswer(StudentAnswerDto studentAnswerDto);

    List<StudentAnswerAtAllLesson> findAllStudentAnswer();
}
