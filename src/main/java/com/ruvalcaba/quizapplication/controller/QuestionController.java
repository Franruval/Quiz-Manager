package com.ruvalcaba.quizapplication.controller;

import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    // getAllQuestions will show all questions in a JSON format
    @GetMapping("allQuestions") // URL:http://localhost:8080/questions/allQuestions
    public ResponseEntity<List<QuestionModel>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    // Shows all questions by category
    @GetMapping("category/{category}") // Available categories: JAVA, PYTHON
    public ResponseEntity<List<QuestionModel>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    // Shows all questions by difficulty
    @GetMapping("difficulty/{difficulty}") // Available difficulties: EASY, MEDIUM, HARD
    public List<QuestionModel> getQuestionsByDifficulty(@PathVariable String difficulty){
        return questionService.getQuestionsByDifficulty(difficulty);
    }

    // Adds a question to the database with the fields specified in a JSON format
    @PostMapping("add-question")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionModel question){
        return questionService.addQuestion(question);
    }
}
