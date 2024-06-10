package com.ruvalcaba.quizapplication.exception;

public class QuizInvalidSizeException extends RuntimeException{
    public QuizInvalidSizeException(String message) {
        super(message);
    }

    public QuizInvalidSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
