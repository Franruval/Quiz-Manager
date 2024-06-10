package com.ruvalcaba.quizapplication.service.implementation;

import com.ruvalcaba.quizapplication.DAO.QuestionDAO;
import com.ruvalcaba.quizapplication.exception.QuestionInvalidFormatException;
import com.ruvalcaba.quizapplication.exception.QuestionNotFoundException;
import com.ruvalcaba.quizapplication.exception.QuestionRepeatedIDException;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // or @Component
public class QuestionServiceImpl implements QuestionService {

    QuestionDAO questionDAO;

    // Dependency injection
    public QuestionServiceImpl(QuestionDAO questionDAO){
        this.questionDAO = questionDAO;
    }

    // Fetches all available questions
    @Override
    public List<QuestionModel> getAllQuestions(){
        return questionDAO.findAll();
    }

    // Will throw an exception if no questions are found
    @Override
    public List<QuestionModel> getQuestionsByCategory(String category) {
        if(questionDAO.findByCategory(category).isEmpty())
            throw new QuestionNotFoundException("No questions match the specified category");
        return questionDAO.findByCategory(category);
    }

    // Will throw an exception if the id is not found
    @Override
    public List<QuestionModel> getQuestionsByDifficulty(String difficulty) {
        if(questionDAO.findByDifficultyLevel(difficulty).isEmpty())
            throw new QuestionNotFoundException("No questions match the specified difficulty");
        return questionDAO.findByDifficultyLevel(difficulty);
    }

    // Will throw exception if any of the fields of the QuestionModel are null or if the ID is repeated
    @Override
    public String addQuestion(QuestionModel q) {
        if(q.getCategory()==null || q.getQuestionTitle()==null || q.getCorrectAnswer()==null ||
                q.getDifficultyLevel()==null || q.getOption1()==null || q.getOption2()==null ||
                q.getOption3()==null || q.getOption4()==null)
            throw new QuestionInvalidFormatException("Invalid format, some fields are null");

        if(questionDAO.findById(q.getId()).isPresent())
            throw new QuestionRepeatedIDException("Question with specified ID already exists");

        questionDAO.save(q);
        return "Question added successfully";
    }

    // Will throw an exception if the specified ID is not found
    @Override
    public String deleteQuestion(Long id) {
        if(questionDAO.findById(id).isEmpty())
            throw new QuestionNotFoundException("The question with specified ID doesn't exists");
        questionDAO.deleteById(id);
        return "Question with id=" + id + " deleted successfully";
    }

    // Need to specify all fields including ID or will throw an exception
    @Override
    public String updateQuestion(QuestionModel q) {
        if(q.getId()==null || q.getCategory()==null || q.getQuestionTitle()==null || q.getCorrectAnswer()==null ||
                q.getDifficultyLevel()==null || q.getOption1()==null || q.getOption2()==null ||
                q.getOption3()==null || q.getOption4()==null)
            throw new QuestionInvalidFormatException("Invalid format, some fields are null");

        questionDAO.save(q);
        return "Question updated successfully";
    }
}
