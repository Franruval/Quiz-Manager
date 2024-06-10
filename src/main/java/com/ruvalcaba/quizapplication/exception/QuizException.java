package com.ruvalcaba.quizapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class QuizException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
}
