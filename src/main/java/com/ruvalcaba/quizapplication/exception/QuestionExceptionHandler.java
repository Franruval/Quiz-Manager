package com.ruvalcaba.quizapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuestionExceptionHandler {

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<Object> handleQuestionNotFoundException(QuestionNotFoundException e){

        QuestionException qe = new QuestionException(e.getMessage(),e.getCause(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(qe,HttpStatus.NOT_FOUND);
    }
}
