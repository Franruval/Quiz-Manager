package com.ruvalcaba.quizapplication.service;

import com.ruvalcaba.quizapplication.DAO.QuestionDAO;
import com.ruvalcaba.quizapplication.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // or @Component
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    public List<QuestionModel> getAllQuestions(){
        return questionDAO.findAll();
    }

    public List<QuestionModel> getQuestionsByCategory(String category) {
        return questionDAO.findByCategory(category);
    }

    public List<QuestionModel> getQuestionsByDifficulty(String difficulty) {
        return questionDAO.findByDifficultyLevel(difficulty);
    }

    public String addQuestion(QuestionModel question) {
        questionDAO.save(question);
        return "Success!";
    }
}
