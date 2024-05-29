package com.ruvalcaba.quizapplication;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="question")
public class QuestionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String category;
    private String difficulty_level;
    private String question_title;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correct_answer;
}
