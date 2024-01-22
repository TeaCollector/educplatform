package ru.rtstudy.educplatformsecurity.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.ChangeStudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.AllStudentAnswers;
import ru.rtstudy.educplatformsecurity.exception.AnswersNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.CourseNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.ResolveAllTaskException;
import ru.rtstudy.educplatformsecurity.model.Grade;
import ru.rtstudy.educplatformsecurity.repository.GradeRepository;
import ru.rtstudy.educplatformsecurity.repository.LessonRepository;
import ru.rtstudy.educplatformsecurity.repository.UserCourseRepository;
import ru.rtstudy.educplatformsecurity.service.GradeService;
import ru.rtstudy.educplatformsecurity.util.Util;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final LessonRepository lessonRepository;
    private final UserCourseRepository userCourseRepository;
    private final Util util;

    @Override
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
    public List<AllStudentAnswers> findAllStudentAnswer() {
        Long id = util.findUserFromContext().getId();
        return gradeRepository.getAllStudentAnswer(id)
                .orElseThrow(() -> new AnswersNotFoundException("Answers not found."));
    }

    @Override
    public List<AllStudentAnswers> findAllStudentsAnswerForCourse(Long courseId) {
        Long userId = util.findUserFromContext().getId();
        return gradeRepository.findAllStudentsAnswerForCourse(courseId, userId)
                .orElseThrow(() -> new AnswersNotFoundException("Answers not found."));
    }


    @Override
    public ChangeStudentAnswerDto changeAnswer(Long id, ChangeStudentAnswerDto studentsAnswerDto) {
        Long studentId = util.findUserFromContext().getId();
        gradeRepository.changeAnswer(id, studentsAnswerDto.studentAnswer(), studentId);
        return studentsAnswerDto;
    }

    @Override
    public void finishCourse(Long courseId) {
        Long userId = util.findUserFromContext().getId();
        List<Long> lessonsIds = getAllLessonsId(courseId);
        List<Long> gradesIds = getAllGradesFromCourse(lessonsIds, userId)
                .stream()
                .map(Grade::getId)
                .toList();

        if (new HashSet<>(gradesIds).containsAll(lessonsIds)) {
            userCourseRepository.finishCourse(userId, courseId);
        } else {
            throw new ResolveAllTaskException("Please resolve all task in course and try again.");
        }
    }

    @Override
    public List<Long> getAllLessonsId(Long courseId) {
        return gradeRepository.getAllLessonsId(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found."));
    }

    @Override
    public List<Grade> getAllGradesFromCourse(List<Long> lessonIds, Long userId) {
        return gradeRepository.getGrades(lessonIds, userId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found."));
    }
}

