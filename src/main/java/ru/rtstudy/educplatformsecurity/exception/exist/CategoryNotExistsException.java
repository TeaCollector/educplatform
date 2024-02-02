package ru.rtstudy.educplatformsecurity.exception.exist;

public class CategoryNotExistsException extends RuntimeException {
    public CategoryNotExistsException(String msg) {
        super(msg);
    }
}
