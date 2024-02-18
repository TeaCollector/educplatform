package ru.rtstudy.educplatformsecurity.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.rtstudy.educplatformsecurity.dto.request.MentorAnswerDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.GradeDtoResponse;
import ru.rtstudy.educplatformsecurity.dto.response.GradeStudentDtoResponse;
import ru.rtstudy.educplatformsecurity.exception.author.NotEnoughScoreToAuthorException;
import ru.rtstudy.educplatformsecurity.exception.entity.GradeNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.mentor.GradeWasNotReviewed;
import ru.rtstudy.educplatformsecurity.exception.mentor.MentorAnswerAlreadyExistsException;
import ru.rtstudy.educplatformsecurity.exception.mentor.NoCompletedTasksException;
import ru.rtstudy.educplatformsecurity.exception.student.UserNotMentorException;
import ru.rtstudy.educplatformsecurity.model.*;
import ru.rtstudy.educplatformsecurity.model.constant.DifficultLevel;
import ru.rtstudy.educplatformsecurity.model.constant.Role;
import ru.rtstudy.educplatformsecurity.repository.UserCourseRepository;
import ru.rtstudy.educplatformsecurity.service.GradeService;
import ru.rtstudy.educplatformsecurity.service.UserService;
import ru.rtstudy.educplatformsecurity.util.Util;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MentorServiceImplTest {

    @InjectMocks
    private MentorServiceImpl mentorService;

    @Mock
    private UserCourseRepository userCourseRepository;
    @Mock
    private Util util;
    @Mock
    private GradeService gradeService;
    @Mock
    private UserService userService;

    private final User mentor = User.builder()
            .id(1L)
            .email("test@test.com")
            .password("test")
            .firstName("Иван")
            .lastName("Иванов")
            .role(Role.ROLE_MENTOR)
            .build();

    private final User student = User.builder()
            .id(1L)
            .email("test@test.com")
            .password("test")
            .firstName("Владимир")
            .lastName("Иванов")
            .role(Role.ROLE_STUDENT)
            .build();

    private final Course course = Course.builder()
            .id(1L)
            .title("Курс Java")
            .description("Описание курса Java")
            .duration((short) 52)
            .difficult(Difficult.builder()
                    .id(1L)
                    .difficultLevel(DifficultLevel.MEDIUM)
                    .build())
            .category(Category.builder()
                    .id(1L)
                    .title("BACKEND")
                    .build())
            .build();

    private final List<UserCourse> userCourses = List.of(
            UserCourse.builder()
                    .id(1L)
                    .user(mentor)
                    .course(course)
                    .endCourse(true)
                    .mentorCourse(true)
                    .build()
    );

    private final Grade gradeWithReview = Grade.builder()
            .id(1L)
            .student(student)
            .mentor(mentor)
            .lesson(Lesson.builder()
                    .id(1L)
                    .title("История языка Java")
                    .description("Язык Java возник в...")
                    .fileName("file.mp4")
                    .course(course)
                    .taskId(Task.builder()
                            .id(1L)
                            .description("Задание к уроку")
                            .build())
                    .build())
            .grade((byte) 8)
            .rework(false)
            .studentAnswer("Ответ студента")
            .mentorAnswer("Комментарий ментора")
            .build();

    @Test
    @DisplayName("Тест: Получение всех курсов, для которых пользователь является ментором")
    void getAllCoursesForMentor_userIsMentor_successfulGet() {

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(Optional.of(userCourses)).when(userCourseRepository).findAllByUserAndMentorCourse(mentor, true);

        var receivedCoursesForMentor = mentorService.getAllCoursesForMentor();

        Assertions.assertEquals(receivedCoursesForMentor, List.of(course));
    }

    @Test
    @DisplayName("Тест: Получение ошибки при попытке получения всех курсов, для которых пользователь является ментором, при отсутствии прав менторства")
    void getAllCoursesForMentor_userIsNotMentor_throwsException() {

        doReturn(student).when(util).findUserFromContext();
        doReturn(Optional.empty()).when(userCourseRepository).findAllByUserAndMentorCourse(student, true);

        Assertions.assertThrows(UserNotMentorException.class, () -> mentorService.getAllCoursesForMentor());
    }

    @Test
    @DisplayName("Тест: Получение всех ответов студентов за все курсы пользователя-ментора")
    void getAllAnswersForMentorCourses_userIsMentor_thereAreCompletedTasks_allGradesAreFound_successfulGet() {

        var courseId = 1L;

        var expectedGradeDto = GradeDtoResponse.builder()
                .lessonId(1L)
                .description("Задание к уроку")
                .grade((byte) 8)
                .rework(false)
                .studentAnswer("Ответ студента")
                .mentorAnswer("Комментарий ментора")
                .build();

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(Optional.of(userCourses)).when(userCourseRepository).findAllByUserAndMentorCourse(mentor, true);
        doReturn(Optional.of(List.of(gradeWithReview))).when(gradeService).findAllGradesByCourseId(courseId);
        doReturn(Optional.of(expectedGradeDto)).when(gradeService).getGradeById(gradeWithReview.getId());

        var studentsAnswers = mentorService.getAllAnswersForMentorCourses();

        Assertions.assertEquals(List.of(expectedGradeDto), studentsAnswers);

    }

    @Test
    @DisplayName("Тест: Получение ошибки при попытке получения всех ответов студентов за все курсы пользователя-ментора т.к. оценки не существует")
    void getAllAnswersForMentorCourses_userIsMentor_thereAreCompletedTasks_gradeNotFound_throwsException() {

        var courseId = 1L;

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(Optional.of(userCourses)).when(userCourseRepository).findAllByUserAndMentorCourse(mentor, true);
        doReturn(Optional.of(List.of(gradeWithReview))).when(gradeService).findAllGradesByCourseId(courseId);
        doReturn(Optional.empty()).when(gradeService).getGradeById(gradeWithReview.getId());

        Assertions.assertThrows(GradeNotFoundException.class, () -> mentorService.getAllAnswersForMentorCourses());
    }

    @Test
    @DisplayName("Тест: Получение ошибки при попытке получения всех ответов студентов за все курсы пользователя-ментора т.к. ответов студентов нет")
    void getAllAnswersForMentorCourses_userIsMentor_thereAreNoCompletedTasks_throwsException() {

        var courseId = 1L;

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(Optional.of(userCourses)).when(userCourseRepository).findAllByUserAndMentorCourse(mentor, true);
        doReturn(Optional.empty()).when(gradeService).findAllGradesByCourseId(courseId);

        Assertions.assertThrows(NoCompletedTasksException.class, () -> mentorService.getAllAnswersForMentorCourses());
    }

    @Test
    @DisplayName("Тест: Получение ошибки при попытке получения всех ответов студентов за все курсы пользователя-ментора т.к. пользователь не ментор")
    void getAllAnswersForMentorCourses_userIsNotMentor_throwsException() {

        doReturn(student).when(util).findUserFromContext();
        doReturn(Optional.empty()).when(userCourseRepository).findAllByUserAndMentorCourse(student, true);

        Assertions.assertThrows(UserNotMentorException.class, () -> mentorService.getAllAnswersForMentorCourses());
    }

    @Test
    @DisplayName("Тест: Получение ответов студентов по ID курса, где текущий пользователь ментор")
    void getAllAnswersForCourse_userIsMentor_thereAreCompletedTasks_successfulGet() {

        var courseId = 1L;

        var studentAnswers = List.of(
                GradeStudentDtoResponse.builder()
                        .firstName("Роман")
                        .lastName("Попов")
                        .lessonId(1L)
                        .description("Описание задания")
                        .grade((byte) 3)
                        .rework(true)
                        .studentAnswer("Неправильный ответ на задание")
                        .mentorAnswer("Переделывай")
                        .build(),
                GradeStudentDtoResponse.builder()
                        .firstName("Дмитрий")
                        .lastName("Максимов")
                        .lessonId(1L)
                        .description("Описание другого задания")
                        .studentAnswer("Правильный ответ на задание")
                        .build()
        );

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(true).when(userCourseRepository).isMentorForCourse(mentor.getId(), courseId);
        doReturn(Optional.of(studentAnswers)).when(gradeService).findAllStudentsAnswersByCourseId(courseId);

        var receivedStudentAnswers = mentorService.getAllAnswersForCourse(courseId);

        Assertions.assertEquals(studentAnswers, receivedStudentAnswers);
    }

    @Test
    @DisplayName("Тест: Получение ошибки на отсутствии ответов студентов к курсу, где текущий пользователь ментор")
    void getAllAnswersForCourse_userIsMentor_thereAreNoCompletedTasks_throwsException() {

        var courseId = 1L;

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(true).when(userCourseRepository).isMentorForCourse(mentor.getId(), courseId);
        doReturn(Optional.empty()).when(gradeService).findAllStudentsAnswersByCourseId(courseId);

        Assertions.assertThrows(NoCompletedTasksException.class, () -> mentorService.getAllAnswersForCourse(courseId));
    }

    @Test
    @DisplayName("Тест: Получение ошибки при попытке получения всех ответов за курс, если текущий пользователь не ментор")
    void getAllAnswersForCourse_userIsNotMentor_throwsException() {

        var courseId = 1L;

        doReturn(student).when(util).findUserFromContext();
        doReturn(false).when(userCourseRepository).isMentorForCourse(student.getId(), courseId);

        Assertions.assertThrows(UserNotMentorException.class, () -> mentorService.getAllAnswersForCourse(courseId));
    }

    @Test
    @DisplayName("Тест: Получение ответов студентов по ID урока, где текущий пользователь ментор")
    void getAllAnswersForLesson_userIsMentor_thereAreCompletedTasks_successfulGet() {

        var lessonId = 1L;

        var studentAnswers = List.of(
                GradeStudentDtoResponse.builder()
                        .firstName("Роман")
                        .lastName("Попов")
                        .lessonId(1L)
                        .description("Описание задания")
                        .grade((byte) 3)
                        .rework(true)
                        .studentAnswer("Неправильный ответ на задание")
                        .mentorAnswer("Переделывай")
                        .build(),
                GradeStudentDtoResponse.builder()
                        .firstName("Дмитрий")
                        .lastName("Максимов")
                        .lessonId(1L)
                        .description("Описание другого задания")
                        .studentAnswer("Правильный ответ на задание")
                        .build()
        );

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(true).when(userCourseRepository).isMentorForLesson(mentor.getId(), lessonId);
        doReturn(Optional.of(studentAnswers)).when(gradeService).findAllStudentsAnswersByLessonId(lessonId);

        var receivedStudentAnswers = mentorService.getAllAnswersForLesson(lessonId);

        Assertions.assertEquals(studentAnswers, receivedStudentAnswers);
    }

    @Test
    @DisplayName("Тест: Получение ошибки на отсутствии ответов студентов к уроку, где текущий пользователь ментор")
    void getAllAnswersForLesson_userIsMentor_thereAreNoCompletedTasks_throwsException() {

        var lessonId = 1L;

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(true).when(userCourseRepository).isMentorForLesson(mentor.getId(), lessonId);
        doReturn(Optional.empty()).when(gradeService).findAllStudentsAnswersByLessonId(lessonId);

        Assertions.assertThrows(NoCompletedTasksException.class, () -> mentorService.getAllAnswersForLesson(lessonId));
    }

    @Test
    @DisplayName("Тест: Получение ошибки при попытке получения всех ответов за урок, если текущий пользователь не ментор")
    void getAllAnswersForLesson_userIsNotMentor_throwsException() {

        var lessonId = 1L;

        doReturn(student).when(util).findUserFromContext();
        doReturn(false).when(userCourseRepository).isMentorForLesson(student.getId(), lessonId);

        Assertions.assertThrows(UserNotMentorException.class, () -> mentorService.getAllAnswersForLesson(lessonId));
    }

    @Test
    @DisplayName("Тест: Успешное оценивание ответа студента ментором")
    void reviewStudentAnswer_userIsMentor_gradeIsNotReviewed_successfulReview() {

        var gradeId = 1L;

        var gradeToReview = Grade.builder()
                .id(2L)
                .student(student)
                .lesson(Lesson.builder()
                        .id(1L)
                        .title("История языка Java")
                        .description("Язык Java возник в...")
                        .fileName("file.mp4")
                        .course(course)
                        .taskId(Task.builder()
                                .id(1L)
                                .description("Задание к уроку")
                                .build())
                        .build())
                .studentAnswer("Ответ студента")
                .build();

        var mentorReview = MentorAnswerDtoRequest.builder()
                .grade((byte) 7)
                .rework(false)
                .mentorAnswer("Комментарий ментора")
                .build();

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(gradeToReview).when(gradeService).getReferenceById(gradeId);
        doReturn(true).when(userCourseRepository).isMentorForLesson(mentor.getId(), gradeToReview.getLesson().getId());

        var expectedReturn = mentorService.reviewStudentAnswer(gradeId, mentorReview);

        verify(gradeService, times(1)).addMentorReview(gradeId, mentorReview.grade(), mentorReview.rework(), mentorReview.mentorAnswer(), mentor);
        Assertions.assertEquals(expectedReturn, mentorReview);
    }

    @Test
    @DisplayName("Тест: Получение ошибки при попытке оценивания, т.к. ответ студента уже был оценён ранее")
    void reviewStudentAnswer_userIsMentor_gradeIsAlreadyReviewed_throwsException() {

        var gradeId = 1L;

        var mentorReview = MentorAnswerDtoRequest.builder()
                .grade((byte) 7)
                .rework(false)
                .mentorAnswer("Комментарий ментора")
                .build();

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(gradeWithReview).when(gradeService).getReferenceById(gradeId);
        doReturn(true).when(userCourseRepository).isMentorForLesson(mentor.getId(), gradeWithReview.getLesson().getId());

        Assertions.assertThrows(MentorAnswerAlreadyExistsException.class, () -> mentorService.reviewStudentAnswer(gradeId, mentorReview));
    }

    @Test
    @DisplayName("Тест: Получение ошибки при попытке оценивания, т.к. пользователь не является ментором")
    void reviewStudentAnswer_userIsNotMentor_throwsException() {

        var gradeId = 2L;

        var notMentor = User.builder()
                .id(2L)
                .email("test@test.com")
                .password("test")
                .firstName("Иван")
                .lastName("Иванов")
                .role(Role.ROLE_STUDENT)
                .build();

        var gradeToReview = Grade.builder()
                .id(2L)
                .student(student)
                .lesson(Lesson.builder()
                        .id(1L)
                        .title("История языка Java")
                        .description("Язык Java возник в...")
                        .fileName("file.mp4")
                        .course(course)
                        .taskId(Task.builder()
                                .id(1L)
                                .description("Задание к уроку")
                                .build())
                        .build())
                .studentAnswer("Ответ студента")
                .build();

        var mentorReview = MentorAnswerDtoRequest.builder()
                .grade((byte) 7)
                .rework(false)
                .mentorAnswer("Комментарий ментора")
                .build();

        doReturn(notMentor).when(util).findUserFromContext();
        doReturn(gradeToReview).when(gradeService).getReferenceById(gradeId);
        doReturn(false).when(userCourseRepository).isMentorForLesson(notMentor.getId(), gradeToReview.getLesson().getId());

        Assertions.assertThrows(UserNotMentorException.class, () -> mentorService.reviewStudentAnswer(gradeId, mentorReview));
    }

    @Test
    @DisplayName("Тест: Изменение ответа ментора на отправленное задание студента")
    void updateMentorAnswer_userIsMentor_studentAnswerWasReviewed_wasReviewedByThisMentor_successfulUpdate() {

        var gradeId = 1L;

        var mentorReview = MentorAnswerDtoRequest.builder()
                .grade((byte) 7)
                .rework(false)
                .mentorAnswer("Комментарий ментора")
                .build();

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(gradeWithReview).when(gradeService).getReferenceById(gradeId);
        doReturn(true).when(userCourseRepository).isMentorForLesson(mentor.getId(), gradeWithReview.getLesson().getId());

        var expectedReturn = mentorService.updateMentorAnswer(gradeId, mentorReview);

        verify(gradeService, times(1)).updateMentorReview(gradeId, mentorReview.grade(), mentorReview.rework(), mentorReview.mentorAnswer());
        Assertions.assertEquals(expectedReturn, mentorReview);
    }

    @Test
    @DisplayName("Тест: Получение ошибки при попытке обновления комментария ментора, т.к. ответ пользователя был оценён другим ментором")
    void updateMentorAnswer_userIsMentor_studentAnswerWasReviewed_wasReviewedByOtherMentor_throwsException() {

        var gradeId = 2L;

        var otherMentor = User.builder()
                .id(2L)
                .email("test@test.com")
                .password("test")
                .firstName("Георгий")
                .lastName("Иванов")
                .role(Role.ROLE_MENTOR)
                .build();

        var gradeToReview = Grade.builder()
                .id(2L)
                .student(student)
                .lesson(Lesson.builder()
                        .id(1L)
                        .title("История языка Java")
                        .description("Язык Java возник в...")
                        .fileName("file.mp4")
                        .course(course)
                        .taskId(Task.builder()
                                .id(1L)
                                .description("Задание к уроку")
                                .build())
                        .build())
                .grade((byte) 5)
                .rework(true)
                .mentor(otherMentor)
                .studentAnswer("Ответ студента")
                .mentorAnswer("Комментарий ментора")
                .build();

        var mentorReview = MentorAnswerDtoRequest.builder()
                .grade((byte) 7)
                .rework(false)
                .mentorAnswer("Комментарий ментора")
                .build();

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(gradeToReview).when(gradeService).getReferenceById(gradeId);
        doReturn(true).when(userCourseRepository).isMentorForLesson(mentor.getId(), gradeToReview.getLesson().getId());

        Assertions.assertThrows(UserNotMentorException.class, () -> mentorService.updateMentorAnswer(gradeId, mentorReview));
    }

    @Test
    @DisplayName("Тест: Получение ошибки при попытке обновления комментария студента, т.к. ответ пользователя ещё не был никем оценён")
    void updateMentorAnswer_userIsMentor_studentAnswerWasNotReviewed_throwsException() {

        var gradeId = 1L;

        var gradeToReview = Grade.builder()
                .id(1L)
                .student(student)
                .lesson(Lesson.builder()
                        .id(1L)
                        .title("История языка Java")
                        .description("Язык Java возник в...")
                        .fileName("file.mp4")
                        .course(course)
                        .taskId(Task.builder()
                                .id(1L)
                                .description("Задание к уроку")
                                .build())
                        .build())
                .studentAnswer("Ответ студента")
                .build();

        var mentorReview = MentorAnswerDtoRequest.builder()
                .grade((byte) 7)
                .rework(false)
                .mentorAnswer("Комментарий ментора")
                .build();

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(gradeToReview).when(gradeService).getReferenceById(gradeId);
        doReturn(true).when(userCourseRepository).isMentorForLesson(mentor.getId(), gradeToReview.getLesson().getId());

        Assertions.assertThrows(GradeWasNotReviewed.class, () -> mentorService.updateMentorAnswer(gradeId, mentorReview));
    }

    @Test
    @DisplayName("Тест: Получение ошибки при попытке обновления комментария студента, т.к. пользователь не является ментором")
    void updateMentorAnswer_userIsMentor_studentAnswerWasNotReviewed() {

        var gradeId = 1L;

        var notMentor = User.builder()
                .id(2L)
                .email("test@test.com")
                .password("test")
                .firstName("Иван")
                .lastName("Иванов")
                .role(Role.ROLE_STUDENT)
                .build();

        var mentorReview = MentorAnswerDtoRequest.builder()
                .grade((byte) 7)
                .rework(false)
                .mentorAnswer("Комментарий ментора")
                .build();

        doReturn(notMentor).when(util).findUserFromContext();
        doReturn(gradeWithReview).when(gradeService).getReferenceById(gradeId);
        doReturn(false).when(userCourseRepository).isMentorForLesson(notMentor.getId(), gradeWithReview.getLesson().getId());

        Assertions.assertThrows(UserNotMentorException.class, () -> mentorService.updateMentorAnswer(gradeId, mentorReview));
    }

    @Test
    @DisplayName("Тест: Получение прав на авторство после достижения нужного количества ответов")
    void upgradeToMentor_userHasEnoughAnswers_successfulUpgrade() {

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(100).when(gradeService).countAllAnswersByMentorUserId(mentor.getId());

        mentorService.upgradeToAuthor();

        verify(userService, times(1)).changeUserRole(mentor.getId(), Role.ROLE_AUTHOR);
    }

    @Test
    @DisplayName("Тест: Получение ошибки при отсутствии необходимого кол-ва проверенных заданий")
    void upgradeToMentor_userHasNotEnoughAnswers_throwsException() {

        doReturn(mentor).when(util).findUserFromContext();
        doReturn(80).when(gradeService).countAllAnswersByMentorUserId(mentor.getId());

        Assertions.assertThrows(NotEnoughScoreToAuthorException.class, () -> mentorService.upgradeToAuthor());
    }
}