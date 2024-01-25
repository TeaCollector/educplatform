package ru.rtstudy.educplatform.minioservice.exception;

public class NotAuthenticatedException extends RuntimeException {
    public NotAuthenticatedException(String msg) {
        super(msg);
    }
}
