package ru.rtstudy.educplatformsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String msg) {
        super(msg);
    }
}
