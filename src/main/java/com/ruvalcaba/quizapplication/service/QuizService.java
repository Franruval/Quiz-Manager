package com.ruvalcaba.quizapplication.service;

import com.ruvalcaba.quizapplication.DAO.QuestionDAO;
import com.ruvalcaba.quizapplication.DAO.QuizDAO;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.model.QuizModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDAO quizDAO;
    @Autowired
    QuestionDAO questionDAO;


    public ResponseEntity<String> createQuiz(String category, int totalQuestions, String title) {
        List<QuestionModel> questions = questionDAO.findRandomQuestionsByCategory(category, totalQuestions);

        QuizModel quiz = new QuizModel();
        quiz.setQuizTitle(title);
        quiz.setQuestions(questions);
        quizDAO.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}
