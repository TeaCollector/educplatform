package ru.rtstudy.educplatformsecurity.exception.entity;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String msg) {
        super(msg);
    }
}
