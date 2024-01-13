package ru.rtstudy.educplatformsecurity.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String msg) {
        super(msg);
    }
}
