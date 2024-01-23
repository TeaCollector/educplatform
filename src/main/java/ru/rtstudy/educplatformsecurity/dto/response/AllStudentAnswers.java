package ru.rtstudy.educplatformsecurity.dto.response;

public record AllStudentAnswers(String task,
                                byte grade,
                                boolean rework,
                                String studentAnswer,
                                String mentorsAnswer) {
}
