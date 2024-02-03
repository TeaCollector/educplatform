package ru.rtstudy.educplatformsecurity.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.ChangeStudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.AllStudentAnswers;
import ru.rtstudy.educplatformsecurity.service.GradeService;
import ru.rtstudy.educplatformsecurity.service.UserCourseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentResponseBuilder {

    private final UserCourseService userCourseService;
    private final GradeService gradeService;

    public HttpStatus enterOnCourse(Long courseId) {
        userCourseService.enterOnCourse(courseId);
        return HttpStatus.CREATED;
    }

    public HttpStatus finishCourse(Long courseId) {
        gradeService.finishCourse(courseId);
        return HttpStatus.CREATED;
    }

    public StudentAnswerDto sendAnswer(StudentAnswerDto studentAnswerDto) {
        return gradeService.sendAnswer(studentAnswerDto);
    }


    public List<AllStudentAnswers> findAllStudentAnswer() {
        return gradeService.findAllStudentAnswer();
    }

    public List<AllStudentAnswers> findAllStudentsAnswerForCourse(Long courseId) {
       return gradeService.findAllStudentsAnswerForCourse(courseId);
    }

    public ChangeStudentAnswerDto changeAnswer(Long id, ChangeStudentAnswerDto studentsAnswerDto) {
        return gradeService.changeAnswer(id, studentsAnswerDto);
    }

    public HttpStatus upgradeToMentor(Long courseId) {
        userCourseService.upgradeToMentor(courseId);
        return HttpStatus.CREATED;
    }
}
