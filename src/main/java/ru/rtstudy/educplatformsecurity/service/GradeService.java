package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.ChangeStudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.AllStudentAnswers;
import ru.rtstudy.educplatformsecurity.model.Grade;

import java.util.List;

public interface GradeService {

    StudentAnswerDto sendAnswer(StudentAnswerDto studentAnswerDto);

    List<AllStudentAnswers> findAllStudentAnswer();

    List<AllStudentAnswers> findAllStudentsAnswerForCourse(Long id);

    ChangeStudentAnswerDto changeAnswer(Long id, ChangeStudentAnswerDto studentsAnswerDto);

    void finishCourse(Long id);

    List<Long> getAllLessonsId(Long courseId);

    List<Grade> getAllGradesByLesson(List<Long> lessonIds, Long userId);
}
