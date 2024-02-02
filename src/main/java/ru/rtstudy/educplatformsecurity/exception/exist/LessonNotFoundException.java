package ru.rtstudy.educplatformsecurity.exception.exist;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(String msg) {
        super(msg);
    }
}
