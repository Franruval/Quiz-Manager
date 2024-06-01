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

    // This method deletes a question from the database specifying the id
    @PostMapping("delete-question")
    // Example delete question URL: // http://localhost:8080/questions/delete-question?id=17
    public ResponseEntity<String> deleteQuestion(@RequestParam Integer id){
        return questionService.deleteQuestion(id);
    }
}

/*
Format example of adding a question
    {
        "category": "PYTHON",
        "difficultyLevel": "MEDIUM",
        "questionTitle": "In Python, which loop is used for iterating over a sequence?",
        "option1": "A. for loop",
        "option2": "B. while loop",
        "option3": "C. do-while loop",
        "option4": "D. foreach loop",
        "correctAnswer": "B"
    }

 */
