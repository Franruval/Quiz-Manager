package com.ruvalcaba.quizapplication.controller;

import com.ruvalcaba.quizapplication.QuestionModel;
import com.ruvalcaba.quizapplication.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public List<QuestionModel> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public List<QuestionModel> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @GetMapping("difficulty/{difficulty}")
    public List<QuestionModel> getQuestionsByDifficulty(@PathVariable String difficulty){
        return questionService.getQuestionsByDifficulty(difficulty);
    }

    @PostMapping("add-question")
    public String addQuestion(@RequestBody QuestionModel question){
        return questionService.addQuestion(question);
    }
}
