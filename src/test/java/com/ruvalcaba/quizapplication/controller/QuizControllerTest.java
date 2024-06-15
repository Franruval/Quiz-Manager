package com.ruvalcaba.quizapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ruvalcaba.quizapplication.model.Answer;
import com.ruvalcaba.quizapplication.model.QuestionModelWrapper;
import com.ruvalcaba.quizapplication.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = QuizController.class)
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuizService quizService;

    @Test
    void QuizController_createQuiz_returnsCreated() throws Exception{
        when(quizService.createQuiz("Category",2,"Title")).thenReturn("Success");

        this.mockMvc.perform(post("/quiz/create")
                        .param("category", "Category")
                        .param("totalQuestions", "2")
                        .param("title", "Title"))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void QuizController_getQuiz_returnsOk() throws Exception{
        QuestionModelWrapper question1 = new QuestionModelWrapper(
                1L,"Test Title","A","B","C","D");
        QuestionModelWrapper question2 = new QuestionModelWrapper(
                2L,"Test Title","A","B","C","D");

        List<QuestionModelWrapper> quiz = new ArrayList<>();

        quiz.add(question1);
        quiz.add(question2);

        when(quizService.getQuiz(1L)).thenReturn(quiz);

        this.mockMvc.perform(get("/quiz/get/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void QuizController_submitAnswers_returnsOk() throws Exception{
        Answer answer1 = new Answer(1L,"A");
        Answer answer2 = new Answer(1L,"B");

        List<Answer> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(answers);

        when(quizService.calculateScore(1L,answers)).thenReturn(2);
        this.mockMvc.perform(post("/quiz/submit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }
}