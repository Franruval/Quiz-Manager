package com.ruvalcaba.quizapplication.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Answer {
    private Long id;
    private String answer;
}
