package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;

public interface GradeService {

    StudentAnswerDto sendAnswer(StudentAnswerDto studentAnswerDto);

}
