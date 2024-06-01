package com.ruvalcaba.quizapplication.service;

import com.ruvalcaba.quizapplication.DAO.QuestionDAO;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // or @Component
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    public ResponseEntity<List<QuestionModel>> getAllQuestions(){
        try{
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionModel>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public List<QuestionModel> getQuestionsByDifficulty(String difficulty) {
        return questionDAO.findByDifficultyLevel(difficulty);
    }

    public ResponseEntity<String> addQuestion(QuestionModel question) {
        try{
            questionDAO.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try{
            questionDAO.deleteById(id);
            return new ResponseEntity<>("Question" + id + "deleted succesfully", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);

    }
}
