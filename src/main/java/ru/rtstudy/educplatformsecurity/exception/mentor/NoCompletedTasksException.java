package ru.rtstudy.educplatformsecurity.exception.mentor;

public class NoCompletedTasksException extends RuntimeException {
    public NoCompletedTasksException(String message) {
        super(message);
    }
}
