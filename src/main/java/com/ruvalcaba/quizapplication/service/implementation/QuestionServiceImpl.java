package com.ruvalcaba.quizapplication.service.implementation;

import com.ruvalcaba.quizapplication.repository.QuestionRepo;
import com.ruvalcaba.quizapplication.exception.QuestionInvalidFormatException;
import com.ruvalcaba.quizapplication.exception.QuestionNotFoundException;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // or @Component
public class QuestionServiceImpl implements QuestionService {

    QuestionRepo questionRepo;

    // Dependency injection
    public QuestionServiceImpl(QuestionRepo questionRepo){
        this.questionRepo = questionRepo;
    }

    // Fetches all available questions
    @Override
    public List<QuestionModel> getAllQuestions(){
        return questionRepo.findAll();
    }

    // Will throw an exception if no questions are found
    @Override
    public List<QuestionModel> getQuestionsByCategory(String category) {
        if(questionRepo.findByCategory(category).isEmpty())
            throw new QuestionNotFoundException("No questions match the specified category");
        return questionRepo.findByCategory(category);
    }

    // Will throw an exception if the id is not found
    @Override
    public List<QuestionModel> getQuestionsByDifficulty(String difficulty) {
        if(questionRepo.findByDifficultyLevel(difficulty).isEmpty())
            throw new QuestionNotFoundException("No questions match the specified difficulty");
        return questionRepo.findByDifficultyLevel(difficulty);
    }

    // Will throw exception if any of the fields of the QuestionModel are null
    @Override
    public String addQuestion(QuestionModel q) {
        if(q.getCategory()==null || q.getQuestionTitle()==null || q.getCorrectAnswer()==null ||
                q.getDifficultyLevel()==null || q.getOption1()==null || q.getOption2()==null ||
                q.getOption3()==null || q.getOption4()==null)
            throw new QuestionInvalidFormatException("Invalid format, some fields are null");

        questionRepo.save(q);
        return "Question added successfully";
    }

    // Will throw an exception if the specified ID is not found
    @Override
    public String deleteQuestion(Long id) {
        if(questionRepo.findById(id).isEmpty())
            throw new QuestionNotFoundException("The question with specified ID doesn't exists");
        questionRepo.deleteById(id);
        return "Question with id=" + id + " deleted successfully";
    }

    // Need to specify all fields including ID or will throw an exception
    @Override
    public String updateQuestion(QuestionModel q) {
        if(q.getId()==null || q.getCategory()==null || q.getQuestionTitle()==null || q.getCorrectAnswer()==null ||
                q.getDifficultyLevel()==null || q.getOption1()==null || q.getOption2()==null ||
                q.getOption3()==null || q.getOption4()==null)
            throw new QuestionInvalidFormatException("Invalid format, some fields are null");

        questionRepo.save(q);
        return "Question updated successfully";
    }
}
