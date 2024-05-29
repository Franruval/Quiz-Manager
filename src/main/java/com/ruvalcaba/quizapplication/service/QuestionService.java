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

    public List<QuestionModel> getQuestionsByCategory(){
        return questionDAO.findAll();
    }
}
