package ru.rtstudy.educplatformsecurity.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.request.MentorAnswerDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.GradeDtoResponse;
import ru.rtstudy.educplatformsecurity.dto.response.GradeStudentDtoResponse;
import ru.rtstudy.educplatformsecurity.service.MentorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorResponseBuilder {

    private final MentorService mentorService;

    public List<GradeDtoResponse> getAllAnswersForMentorCourses() {
        return mentorService.getAllAnswersForMentorCourses();
    }

    public List<GradeStudentDtoResponse> getAllAnswersForCourse(Long id) {
        return mentorService.getAllAnswersForCourse(id);
    }

    public List<GradeStudentDtoResponse> getAllAnswersForLesson(Long id) {
        return mentorService.getAllAnswersForLesson(id);
    }

    public MentorAnswerDtoRequest reviewStudentAnswer(Long id, MentorAnswerDtoRequest mentorAnswerDtoRequest) {
        return mentorService.reviewStudentAnswer(id, mentorAnswerDtoRequest);
    }

    public MentorAnswerDtoRequest updateMentorAnswer(Long id, MentorAnswerDtoRequest mentorAnswerDtoRequest) {
        return mentorService.updateMentorAnswer(id, mentorAnswerDtoRequest);
    }

    public HttpStatus upgradeToAuthor() {
        mentorService.upgradeToAuthor();
        return HttpStatus.CREATED;
    }
}
