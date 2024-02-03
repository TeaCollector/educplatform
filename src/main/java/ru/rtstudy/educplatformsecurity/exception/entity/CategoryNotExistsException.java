package ru.rtstudy.educplatformsecurity.exception.entity;

public class CategoryNotExistsException extends RuntimeException {
    public CategoryNotExistsException(String msg) {
        super(msg);
    }
}
