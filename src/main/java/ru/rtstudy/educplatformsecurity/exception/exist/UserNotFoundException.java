package ru.rtstudy.educplatformsecurity.exception.exist;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}