package com.ruvalcaba.quizapplication.controller;

import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.response.ResponseHandler;
import com.ruvalcaba.quizapplication.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("questions")
public class QuestionController {

    QuestionService questionService;

    public QuestionController(QuestionService questionService){
        this.questionService=questionService;
    }

    // getAllQuestions will show all questions in a JSON format
    @GetMapping("all") // URL:http://localhost:8080/questions/all
    public ResponseEntity<Object> getAllQuestions(){
        return ResponseHandler.responseBuilder(
                "List of all available questions",
                HttpStatus.OK,
                questionService.getAllQuestions());
    }

    // Shows all questions by category
    @GetMapping("category/{category}") // Available categories: JAVA, PYTHON
    public ResponseEntity<Object> getQuestionsByCategory(@PathVariable String category){
        return ResponseHandler.responseBuilder(
                "Questions filtered by category",
                HttpStatus.OK,
                questionService.getQuestionsByCategory(category));
    }

    // Shows all questions by difficulty
    @GetMapping("difficulty/{difficulty}") // Available difficulties: EASY, MEDIUM, HARD
    public ResponseEntity<Object> getQuestionsByDifficulty(@PathVariable String difficulty){
        return ResponseHandler.responseBuilder(
                "Questions filtered by difficulty",
                HttpStatus.OK,
                questionService.getQuestionsByDifficulty(difficulty));
    }

    // Adds a question to the database with the fields specified in a JSON format
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionModel question){
        return new ResponseEntity<>(questionService.addQuestion(question),HttpStatus.CREATED);
    }

    // This method deletes a question from the database specifying the id
    @DeleteMapping("delete") // Example delete question URL: // http://localhost:8080/questions/delete?id=17
    public ResponseEntity<String> deleteQuestion(@RequestParam Long id){
        return new ResponseEntity<>(questionService.deleteQuestion(id), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<String> updateQuestion(@RequestBody QuestionModel question){
        return new ResponseEntity<>(questionService.updateQuestion(question), HttpStatus.OK);
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
