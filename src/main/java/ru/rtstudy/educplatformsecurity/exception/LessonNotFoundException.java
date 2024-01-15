package ru.rtstudy.educplatformsecurity.exception;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(String msg) {
        super(msg);
    }
}
