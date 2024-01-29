package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.ChangeStudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.AllStudentAnswers;
import ru.rtstudy.educplatformsecurity.exception.*;
import ru.rtstudy.educplatformsecurity.model.Grade;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.model.User;
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
    @Transactional
    public StudentAnswerDto sendAnswer(StudentAnswerDto studentAnswerDto) {
        User user = util.findUserFromContext();
        Lesson lesson = lessonRepository.findById(studentAnswerDto.lessonId())
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found."));
        boolean onCourse = userCourseRepository.onCourse(lesson.getCourse().getId(), user.getId());
        if (onCourse) {
            Grade grade = Grade.builder()
                    .lesson(lesson)
                    .student(user)
                    .studentAnswer(studentAnswerDto.studentAnswer())
                    .build();
            gradeRepository.save(grade);
        } else {
            throw new EnterOnCourseException("Please enter on course.");
        }
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
        List<Long> gradesIds = getAllGradesByLesson(lessonsIds, userId)
                .stream()
                .map(lessonId -> lessonId.getLesson().getId())
                .toList();

        log.info("STUDENTS LessonsIds: {}", lessonsIds);
        log.info("STUDENTS GRADES: {}", gradesIds);

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
    public List<Grade> getAllGradesByLesson(List<Long> lessonIds, Long userId) {
        return gradeRepository.getGrades(lessonIds, userId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found."));
    }
}

