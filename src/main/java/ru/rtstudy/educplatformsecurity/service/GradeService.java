package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.ChangeStudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.AllStudentAnswers;

import java.util.List;

public interface GradeService {

    StudentAnswerDto sendAnswer(StudentAnswerDto studentAnswerDto);

    List<AllStudentAnswers> findAllStudentAnswer();

    List<AllStudentAnswers> findAllStudentsAnswerForCourse(Long id);

    ChangeStudentAnswerDto changeAnswer(Long id, ChangeStudentAnswerDto studentsAnswerDto);
}
