package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.GradeMapper;
import ru.rtstudy.educplatformsecurity.dto.request.MentorAnswerDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.GradeDtoResponse;
import ru.rtstudy.educplatformsecurity.dto.response.GradeStudentDtoResponse;
import ru.rtstudy.educplatformsecurity.exception.*;
import ru.rtstudy.educplatformsecurity.model.Course;
import ru.rtstudy.educplatformsecurity.model.Grade;
import ru.rtstudy.educplatformsecurity.model.User;
import ru.rtstudy.educplatformsecurity.model.UserCourse;
import ru.rtstudy.educplatformsecurity.model.constant.Role;
import ru.rtstudy.educplatformsecurity.repository.GradeRepository;
import ru.rtstudy.educplatformsecurity.repository.UserCourseRepository;
import ru.rtstudy.educplatformsecurity.service.MentorService;
import ru.rtstudy.educplatformsecurity.service.UserService;
import ru.rtstudy.educplatformsecurity.util.Util;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MentorServiceImpl implements MentorService {

    private final UserCourseRepository userCourseRepository;
    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;
    private final Util util;
    private final UserService userService;

    @Override
    public List<Course> getAllCoursesForMentor() {
        User mentor = util.findUserFromContext();
        return userCourseRepository.findAllByUserAndMentorCourse(mentor, true)
                .orElseThrow(() -> new UserNotMentorException("User is not a mentor for any course"))
                .stream()
                .map(UserCourse::getCourse)
                .toList();
    }

    @Override
    public List<GradeDtoResponse> getAllAnswersForMentorCourses() {
        List<Course> mentorCourses = getAllCoursesForMentor();
        return mentorCourses.stream()
                .flatMap(course -> gradeRepository.findAllByCourseId(course.getId())
                        .orElseThrow(() -> new NoCompletedTasksException("There are no completed tasks"))
                        .stream())
                .map(grade -> gradeRepository.getGradeById(grade.getId())
                        .orElseThrow(() -> new GradeNotFoundException("Grade not found")))
                .toList();
    }

    @Override
    public List<GradeStudentDtoResponse> getAllAnswersForCourse(Long courseId) {
        Long mentorId = util.findUserFromContext().getId();
        boolean isMentorForCourse = userCourseRepository.isMentorForCourse(mentorId, courseId);
        if (isMentorForCourse) {
            return gradeRepository.findAllByCourseId(courseId)
                    .orElseThrow(() -> new NoCompletedTasksException("There are no completed tasks"))
                    .stream()
                    .map(gradeMapper::toDto)
                    .toList();
        } else {
            throw new UserNotMentorException("User is not a mentor for any course");
        }
    }

    @Override
    public List<GradeStudentDtoResponse> getAllAnswersForLesson(Long lessonId) {
        Long mentorId = util.findUserFromContext().getId();
        boolean isMentorForLesson = userCourseRepository.isMentorForLesson(mentorId, lessonId);
        if (isMentorForLesson) {
            return gradeRepository.findAllByLessonId(lessonId)
                    .orElseThrow(() -> new NoCompletedTasksException("There are no completed tasks"))
                    .stream()
                    .map(gradeMapper::toDto)
                    .toList();
        } else {
            throw new UserNotMentorException("User is not a mentor for any course");
        }

    }

    @Override
    public MentorAnswerDtoRequest reviewStudentAnswer(Long id, MentorAnswerDtoRequest mentorAnswerDtoRequest) {
        User mentor = util.findUserFromContext();
        Grade gradeToUpdate = gradeRepository.getReferenceById(id);
        boolean isMentorForLesson = userCourseRepository.isMentorForLesson(mentor.getId(), gradeToUpdate.getLesson().getId());
        if (!isMentorForLesson) {
            throw new UserNotMentorException("User is not a mentor for this course lesson");
        }
        if (gradeToUpdate.getMentor() != null) {
            throw new MentorAnswerAlreadyExistsException("This task is already reviewed");
        }
        gradeRepository.addMentorReview(id, mentorAnswerDtoRequest.grade(), mentorAnswerDtoRequest.rework(), mentorAnswerDtoRequest.mentorAnswer(), mentor);
        return mentorAnswerDtoRequest;
    }

    @Override
    public MentorAnswerDtoRequest updateMentorAnswer(Long id, MentorAnswerDtoRequest mentorAnswerDtoRequest) {
        Long mentorId = util.findUserFromContext().getId();
        Grade gradeToUpdate = gradeRepository.getReferenceById(id);
        boolean isMentorForLesson = userCourseRepository.isMentorForLesson(mentorId, gradeToUpdate.getLesson().getId());
        if ((isMentorForLesson) && (gradeToUpdate.getMentor() != null) && (gradeToUpdate.getMentor().getId().equals(mentorId))) {
            gradeRepository.updateMentorReview(id, mentorAnswerDtoRequest.grade(), mentorAnswerDtoRequest.rework(), mentorAnswerDtoRequest.mentorAnswer());
            return mentorAnswerDtoRequest;
        } else {
            throw new UserNotMentorException("User is not a mentor for this course lesson");
        }
    }

    @Override
    public void upgradeToAuthor() {
        Long mentorId = util.findUserFromContext().getId();
        if (gradeRepository.countAllAnswersByMentorUserId(mentorId) >= 100) {
            userService.changeUserRole(mentorId, Role.ROLE_AUTHOR);
        } else {
            throw new NotEnoughScoreToAuthorException("Not enough reviewed tasks to become author");
        }
    }
}