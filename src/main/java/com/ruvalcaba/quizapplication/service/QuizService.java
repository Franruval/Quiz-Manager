package com.ruvalcaba.quizapplication.service;

import com.ruvalcaba.quizapplication.model.Answer;
import com.ruvalcaba.quizapplication.model.QuestionModelWrapper;

import java.util.List;

public interface QuizService {

    public String createQuiz(String category, int totalQuestions, String title);
    public List<QuestionModelWrapper> getQuiz(Long id);
    public Integer calculateScore(Long id, List<Answer> answers);
}
