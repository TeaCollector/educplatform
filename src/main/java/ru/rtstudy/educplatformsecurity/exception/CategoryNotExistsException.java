package ru.rtstudy.educplatformsecurity.exception;

public class CategoryNotExistsException extends RuntimeException {
    public CategoryNotExistsException(String msg) {
        super(msg);
    }
}
