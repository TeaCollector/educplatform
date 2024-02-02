package ru.rtstudy.educplatformsecurity.exception.exist;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String msg) {
        super(msg);
    }
}
