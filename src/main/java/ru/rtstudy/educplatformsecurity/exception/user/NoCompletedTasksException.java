package ru.rtstudy.educplatformsecurity.exception.user;

public class NoCompletedTasksException extends RuntimeException {
    public NoCompletedTasksException(String message) {
        super(message);
    }
}
