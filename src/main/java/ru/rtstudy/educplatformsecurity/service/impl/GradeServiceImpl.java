package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.ChangeStudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.AllStudentAnswers;
import ru.rtstudy.educplatformsecurity.exception.exist.AnswersNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.exist.CourseNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.exist.GradeNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.exist.LessonNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.user.EnterOnCourseException;
import ru.rtstudy.educplatformsecurity.exception.user.ResolveAllTaskException;
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
        log.info("{} send answer on task: {}", user.getEmail(), studentAnswerDto);
        Lesson lesson = lessonRepository.findById(studentAnswerDto.lessonId())
                .orElseThrow(() -> {
                    log.error("Lesson was not found:{}", studentAnswerDto.lessonId(), new LessonNotFoundException("Lesson not found."));
                    return new LessonNotFoundException("Lesson not found.");
                });
        boolean onCourse = userCourseRepository.onCourse(lesson.getCourse().getId(), user.getId());
        if (onCourse) {
            Grade grade = Grade.builder()
                    .lesson(lesson)
                    .student(user)
                    .studentAnswer(studentAnswerDto.studentAnswer())
                    .build();
            gradeRepository.save(grade);
        } else {
            log.error("Student: {} not enter on course by lesson id: {}", user.getEmail(), studentAnswerDto.lessonId(), new EnterOnCourseException("Please enter on course."));
            throw new EnterOnCourseException("Please enter on course.");
        }
        return studentAnswerDto;
    }

    @Override
    public List<AllStudentAnswers> findAllStudentAnswer() {
        log.info("{} find all answer.", util.findUserFromContext().getEmail());
        Long id = util.findUserFromContext().getId();
        return gradeRepository.getAllStudentAnswer(id)
                .orElseThrow(() -> {
                    log.error("Student: {} not send answer.", util.findUserFromContext().getEmail(), new AnswersNotFoundException("Answers not found."));
                    return new AnswersNotFoundException("Answers not found.");
                });
    }

    @Override
    public List<AllStudentAnswers> findAllStudentsAnswerForCourse(Long courseId) {
        User user = util.findUserFromContext();
        log.info("{} find all answer for course id: {}.", user.getEmail(), courseId);
        return gradeRepository.findAllStudentsAnswerForCourse(courseId, user.getId())
                .orElseThrow(() -> {
                    log.error("{} students answer on course id not found: {}",util.findUserFromContext().getEmail(), courseId, new AnswersNotFoundException("Answers not found."));
                    return new AnswersNotFoundException("Answers not found.");
                });
    }


    @Override
    public ChangeStudentAnswerDto changeAnswer(Long id, ChangeStudentAnswerDto studentsAnswerDto) {
        User user = util.findUserFromContext();
        log.info("{} change answer: {} for lesson id: {}.", user.getEmail(), studentsAnswerDto.studentAnswer(), id);
        gradeRepository.changeAnswer(id, studentsAnswerDto.studentAnswer(), user.getId());
        log.info("Answer: {} was successful changed by student: {} ", studentsAnswerDto.studentAnswer(), user.getEmail());
        return studentsAnswerDto;
    }

    @Override
    public void finishCourse(Long courseId) {
        User user = util.findUserFromContext();
        log.info("{} finish course: {}", user.getEmail(), courseId);
        List<Long> lessonsIds = getAllLessonsId(courseId);
        log.debug("{} finish lessons: {}", user.getEmail(), lessonsIds);
        List<Long> gradesIds = getAllGradesByLesson(lessonsIds, user.getId())
                .stream()
                .map(lessonId -> lessonId.getLesson().getId())
                .toList();

        log.debug("{} receive grades: {}", user.getEmail(), gradesIds);

        if (new HashSet<>(gradesIds).containsAll(lessonsIds)) {
            userCourseRepository.finishCourse(user.getId(), courseId);
        } else {
            log.error("{} not resolve all task on course: {}", user.getEmail(), courseId);
            throw new ResolveAllTaskException("Please resolve all task in course and try again.");
        }
    }

    @Override
    public List<Long> getAllLessonsId(Long courseId) {
        log.info("{} get all lessons by course id: {}", util.findUserFromContext().getEmail(), courseId);
        return gradeRepository.getAllLessonsId(courseId)
                .orElseThrow(() -> {
                    log.error("Course not found: {}", courseId, new CourseNotFoundException("Course not found."));
                    return new CourseNotFoundException("Course not found.");
                });
    }

    @Override
    public List<Grade> getAllGradesByLesson(List<Long> lessonIds, Long userId) {
        log.info("{} get all grades by lessonsId id: {}", util.findUserFromContext().getEmail(), lessonIds);
        return gradeRepository.getGrades(lessonIds, userId)
                .orElseThrow(() -> {
                    log.error("Grades by lessonIds: {} was not found.", lessonIds, new GradeNotFoundException("Grade was not found."));
                    return new GradeNotFoundException("Grade was not found.");
                });
    }
}

