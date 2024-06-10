package com.ruvalcaba.quizapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuizExceptionHandler {

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<Object> handleQuizNotFoundException(QuizNotFoundException e){

        QuizException qe = new QuizException(e.getMessage(),e.getCause(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(qe,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuizInvalidSizeException.class)
    public ResponseEntity<Object> handleQuizInvalidSizeException(QuizInvalidSizeException e){

        QuizException qe = new QuizException(e.getMessage(),e.getCause(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(qe,HttpStatus.BAD_REQUEST);
    }
}