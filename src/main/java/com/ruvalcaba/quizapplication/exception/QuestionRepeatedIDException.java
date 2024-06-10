package com.ruvalcaba.quizapplication.exception;

public class QuestionRepeatedIDException extends RuntimeException{
    public QuestionRepeatedIDException(String message) {
        super(message);
    }

    public QuestionRepeatedIDException(String message, Throwable cause) {
        super(message, cause);
    }
}
