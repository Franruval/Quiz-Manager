package com.ruvalcaba.quizapplication.service;

import com.ruvalcaba.quizapplication.DAO.QuestionDAO;
import com.ruvalcaba.quizapplication.DAO.QuizDAO;
import com.ruvalcaba.quizapplication.model.Answer;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.model.QuestionModelWrapper;
import com.ruvalcaba.quizapplication.model.QuizModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<List<QuestionModelWrapper>> getQuiz(Long id) {
        Optional<QuizModel> quiz = quizDAO.findById(id);
        List<QuestionModel> dbQuestions = quiz.get().getQuestions();
        List<QuestionModelWrapper> generatedQuiz = new ArrayList<>();

        for(QuestionModel question : dbQuestions){
            QuestionModelWrapper qmw = new QuestionModelWrapper(
                    question.getId(), question.getQuestionTitle(),
                    question.getOption1(), question.getOption2(),question.getOption3(),question.getOption4());

            generatedQuiz.add(qmw);
        }

        return new ResponseEntity<>(generatedQuiz, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateScore(Long id, List<Answer> answers) {
        Optional<QuizModel> quiz = quizDAO.findById(id);
        List<QuestionModel> dbQuestions = quiz.get().getQuestions();

        int correct=0;
        int i=0;
        for(Answer ans : answers){
            if(ans.getAnswer().equals(dbQuestions.get(i).getCorrectAnswer().substring(0,1)))
                correct++;
            i++;
        }
        return new ResponseEntity<>(correct,HttpStatus.OK);
    }
}
