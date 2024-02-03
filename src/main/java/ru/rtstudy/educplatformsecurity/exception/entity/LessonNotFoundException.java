package ru.rtstudy.educplatformsecurity.exception.entity;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(String msg) {
        super(msg);
    }
}
