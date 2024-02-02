package ru.rtstudy.educplatformsecurity.exception.user;

public class NotEnoughScoreToAuthorException extends RuntimeException {

    public NotEnoughScoreToAuthorException(String message) {
        super(message);
    }
}
