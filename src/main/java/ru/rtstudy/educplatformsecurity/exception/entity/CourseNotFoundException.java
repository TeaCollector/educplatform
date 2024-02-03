package ru.rtstudy.educplatformsecurity.exception.entity;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String msg) {
        super(msg);
    }
}
