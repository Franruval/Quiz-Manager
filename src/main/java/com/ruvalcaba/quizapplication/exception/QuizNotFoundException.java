package com.ruvalcaba.quizapplication.exception;

public class QuizNotFoundException extends RuntimeException{
    public QuizNotFoundException(String message) {
        super(message);
    }

    public QuizNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
