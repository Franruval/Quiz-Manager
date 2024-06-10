package com.ruvalcaba.quizapplication.service;

import com.ruvalcaba.quizapplication.DAO.QuestionDAO;
import com.ruvalcaba.quizapplication.DAO.QuizDAO;
import com.ruvalcaba.quizapplication.exception.QuestionNotFoundException;
import com.ruvalcaba.quizapplication.exception.QuizInvalidSizeException;
import com.ruvalcaba.quizapplication.exception.QuizNotFoundException;
import com.ruvalcaba.quizapplication.model.Answer;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.model.QuestionModelWrapper;
import com.ruvalcaba.quizapplication.model.QuizModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    QuizDAO quizDAO;
    QuestionDAO questionDAO;

    public QuizService(QuizDAO quizDAO, QuestionDAO questionDAO){
        this.quizDAO=quizDAO;
        this.questionDAO=questionDAO;
    }


    public String createQuiz(String category, int totalQuestions, String title) {
        if(questionDAO.findByCategory(category).isEmpty())
            throw new QuestionNotFoundException("No questions with specified category were found");

        if(totalQuestions==0)
            throw new QuizInvalidSizeException("The quiz cannot be of size 0");

        if(totalQuestions > questionDAO.findByCategory(category).size())
            totalQuestions=questionDAO.findByCategory(category).size();

        List<QuestionModel> questions = questionDAO.findRandomQuestionsByCategory(category, totalQuestions);


        QuizModel quiz = new QuizModel();
        quiz.setQuizTitle(title);
        quiz.setQuestions(questions);
        quizDAO.save(quiz);

        return "Success";
    }

    public List<QuestionModelWrapper> getQuiz(Long id) {
        if(quizDAO.findById(id).isEmpty())
            throw new QuizNotFoundException("No quiz with such ID was found");

        Optional<QuizModel> quiz = quizDAO.findById(id);
        List<QuestionModel> dbQuestions = quiz.get().getQuestions();
        List<QuestionModelWrapper> generatedQuiz = new ArrayList<>();

        for(QuestionModel question : dbQuestions){
            QuestionModelWrapper qmw = new QuestionModelWrapper(
                    question.getId(), question.getQuestionTitle(),
                    question.getOption1(), question.getOption2(),question.getOption3(),question.getOption4());

            generatedQuiz.add(qmw);
        }

        return generatedQuiz;
    }

    public ResponseEntity<String> calculateScore(Long id, List<Answer> answers) {
        if(quizDAO.findById(id).isEmpty())
            throw new QuizNotFoundException("No quiz with such ID was found");

        Optional<QuizModel> quiz = quizDAO.findById(id);
        List<QuestionModel> dbQuestions = quiz.get().getQuestions();

        int correct=0;
        int i=0;
        for(Answer ans : answers){
            if(ans.getAnswer().equals(dbQuestions.get(i).getCorrectAnswer().substring(0,1)))
                correct++;
            i++;
        }
        return new ResponseEntity<>("Score: " + correct,HttpStatus.OK);
    }
}
