package com.ruvalcaba.quizapplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class QuizModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String quizTitle;
    @ManyToMany
    private List<QuestionModel> questions;
}
