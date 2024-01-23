package ru.rtstudy.educplatformsecurity.exception;

public class NoCompletedTasksException extends RuntimeException{
    public NoCompletedTasksException(String message) {
        super(message);
    }
}
