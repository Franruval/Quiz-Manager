package com.ruvalcaba.quizapplication.service;

import com.ruvalcaba.quizapplication.DAO.QuestionDAO;
import com.ruvalcaba.quizapplication.exception.QuestionInvalidFormatException;
import com.ruvalcaba.quizapplication.exception.QuestionNotFoundException;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service // or @Component
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    public List<QuestionModel> getAllQuestions(){
        return questionDAO.findAll();
    }

    public List<QuestionModel> getQuestionsByCategory(String category) {
        if(questionDAO.findByCategory(category).isEmpty())
            throw new QuestionNotFoundException("No questions match the specified category");
        return questionDAO.findByCategory(category);
    }

    public List<QuestionModel> getQuestionsByDifficulty(String difficulty) {
        if(questionDAO.findByDifficultyLevel(difficulty).isEmpty())
            throw new QuestionNotFoundException("No questions match the specified difficulty");
        return questionDAO.findByDifficultyLevel(difficulty);
    }

    public String addQuestion(QuestionModel q) {
        if(q.getCategory()==null || q.getQuestionTitle()==null || q.getCorrectAnswer()==null ||
                q.getDifficultyLevel()==null || q.getOption1()==null || q.getOption2()==null ||
                q.getOption3()==null || q.getOption4()==null)
            throw new QuestionInvalidFormatException("Invalid format, some fields are null");

        return "Question added successfully";
    }

    public String deleteQuestion(Integer id) {
        if(questionDAO.findById(id).isEmpty())
            throw new QuestionNotFoundException("The question with specified ID doesn't exists");
        questionDAO.deleteById(id);
        return "Question with id=" + id + " deleted successfully";
    }
}
