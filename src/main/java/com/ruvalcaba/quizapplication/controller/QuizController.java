package com.ruvalcaba.quizapplication.controller;

import com.ruvalcaba.quizapplication.model.Answer;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.model.QuestionModelWrapper;
import com.ruvalcaba.quizapplication.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    // createQuiz will generate a quiz with random questions with the parameters specified via URL
    @PostMapping("create") // Example URL:http://localhost:8080/quiz/create?category=JAVA&totalQuestions=5&title=JAVAQuiz
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int totalQuestions, @RequestParam String title) {

        return new ResponseEntity<>(quizService.createQuiz(category, totalQuestions, title), HttpStatus.CREATED);
    }

    // getQuiz will generate a quiz with only the question title and options for a person to answer
    @GetMapping("get/{id}") // http://localhost:8080/quiz/get/1
    public ResponseEntity<List<QuestionModelWrapper>> getQuiz(@PathVariable Long id) {

        return new ResponseEntity<>(quizService.getQuiz(id), HttpStatus.OK);
    }

    //submitAnswers will submit the answers for a quiz and will return the total correct answers
    @PostMapping("submit/{id}") // http://localhost:8080/quiz/submit/1
    public ResponseEntity<String> submitAnswers(@PathVariable Long id, @RequestBody List<Answer> answers) {

        return new ResponseEntity<>(quizService.calculateScore(id, answers), HttpStatus.OK);
    }
}
    /*
    Example of submitting answers in JSON
    [
    {
        "id":11,
        "answer":"B"
    },
        {
        "id":15,
        "answer":"B"
    },
        {
        "id":13,
        "answer":"A"
    },
        {
        "id":7,
        "answer":"D"
    },
        {
        "id":3,
        "answer":"D"
    }
]
     */

