package ru.rtstudy.educplatformsecurity.dto.response;

public record StudentAnswerAtAllLesson(String task,
                                       byte grade,
                                       boolean rework,
                                       String studentAnswer,
                                       String mentorsAnswer) {
}
