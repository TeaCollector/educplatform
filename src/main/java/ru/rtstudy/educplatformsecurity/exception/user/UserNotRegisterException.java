package ru.rtstudy.educplatformsecurity.exception.user;

public class UserNotRegisterException extends RuntimeException {
    public UserNotRegisterException(String msg) {
        super(msg);
    }
}
