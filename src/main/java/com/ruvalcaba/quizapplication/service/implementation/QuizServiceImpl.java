package com.ruvalcaba.quizapplication.service.implementation;

import com.ruvalcaba.quizapplication.repository.QuestionRepo;
import com.ruvalcaba.quizapplication.repository.QuizRepo;
import com.ruvalcaba.quizapplication.exception.QuestionNotFoundException;
import com.ruvalcaba.quizapplication.exception.QuizInvalidSizeException;
import com.ruvalcaba.quizapplication.exception.QuizNotFoundException;
import com.ruvalcaba.quizapplication.model.Answer;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.model.QuestionModelWrapper;
import com.ruvalcaba.quizapplication.model.QuizModel;
import com.ruvalcaba.quizapplication.service.QuizService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {

    QuizRepo quizRepo;
    QuestionRepo questionRepo;

    public QuizServiceImpl(QuizRepo quizRepo, QuestionRepo questionRepo){
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
    }


    public String createQuiz(String category, int totalQuestions, String title) {
        if(questionRepo.findByCategory(category).isEmpty())
            throw new QuestionNotFoundException("No questions with the specified category were found");

        if(totalQuestions<=0)
            throw new QuizInvalidSizeException("The quiz cannot be of size 0 or less");

        if(totalQuestions > questionRepo.findByCategory(category).size())
            totalQuestions= questionRepo.findByCategory(category).size();

        List<QuestionModel> questions = questionRepo.findRandomQuestionsByCategory(category, totalQuestions);


        QuizModel quiz = new QuizModel();
        quiz.setQuizTitle(title);
        quiz.setQuestions(questions);
        quizRepo.save(quiz);

        return "Success";
    }

    public List<QuestionModelWrapper> getQuiz(Long id) {
        if(quizRepo.findById(id).isEmpty())
            throw new QuizNotFoundException("No quiz with such ID was found");

        Optional<QuizModel> quiz = quizRepo.findById(id);
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

    public Integer calculateScore(Long id, List<Answer> answers) {
        if(quizRepo.findById(id).isEmpty())
            throw new QuizNotFoundException("No quiz with such ID was found");

        Optional<QuizModel> quiz = quizRepo.findById(id);
        List<QuestionModel> dbQuestions = quiz.get().getQuestions();

        int correct=0;
        int i=0;
        for(Answer ans : answers){
            if(ans.getAnswer().equals(dbQuestions.get(i).getCorrectAnswer().substring(0,1)))
                correct++;
            i++;
        }
        return correct;
    }
}
