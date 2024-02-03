package ru.rtstudy.educplatformsecurity.exception.mentor;

public class NotEnoughScoreToMentorException extends RuntimeException {
    public NotEnoughScoreToMentorException(String msg) {
        super(msg);
    }
}
