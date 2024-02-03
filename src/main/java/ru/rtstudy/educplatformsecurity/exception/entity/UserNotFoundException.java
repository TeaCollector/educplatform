package ru.rtstudy.educplatformsecurity.exception.entity;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}