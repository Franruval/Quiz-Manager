package com.ruvalcaba.quizapplication.exception;

public class QuestionInvalidFormatException extends RuntimeException{
    public QuestionInvalidFormatException(String message) {
        super(message);
    }

    public QuestionInvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
