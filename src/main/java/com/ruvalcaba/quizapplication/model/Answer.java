package com.ruvalcaba.quizapplication.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Answer {
    private Integer id;
    private String answer;
}
