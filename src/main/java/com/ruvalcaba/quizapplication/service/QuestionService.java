package com.ruvalcaba.quizapplication.service;

import com.ruvalcaba.quizapplication.exception.QuestionInvalidFormatException;
import com.ruvalcaba.quizapplication.exception.QuestionNotFoundException;
import com.ruvalcaba.quizapplication.model.QuestionModel;

import java.util.List;

public interface QuestionService {
    public List<QuestionModel> getAllQuestions();
    public List<QuestionModel> getQuestionsByCategory(String category);
    public List<QuestionModel> getQuestionsByDifficulty(String difficulty);
    public String addQuestion(QuestionModel q);
    public String deleteQuestion(Long id);
    String updateQuestion(QuestionModel question);
}
