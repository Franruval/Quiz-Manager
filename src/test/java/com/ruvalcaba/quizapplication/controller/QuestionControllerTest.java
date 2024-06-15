package com.ruvalcaba.quizapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionController.class)
@AutoConfigureMockMvc(addFilters = false)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    QuestionService questionService;

    QuestionModel question1;
    QuestionModel question2;
    QuestionModel question3;
    List<QuestionModel> questions = new ArrayList<>();
    List<QuestionModel> questionsByCategory = new ArrayList<>();
    List<QuestionModel> questionsByDifficulty = new ArrayList<>();

    @BeforeEach
    void setUp() {
        question1 = QuestionModel.builder()
                .category("Category 1")
                .questionTitle("Title 1")
                .difficultyLevel("Difficulty 1")
                .option1("a")
                .option2("b")
                .option3("c")
                .option4("d")
                .correctAnswer("a")
                .build();

        question2 = QuestionModel.builder()
                .category("Category 2")
                .questionTitle("Title 2")
                .difficultyLevel("Difficulty 2")
                .option1("a")
                .option2("b")
                .option3("c")
                .option4("d")
                .correctAnswer("a")
                .build();

        question3 = QuestionModel.builder()
                .category("Category 2")
                .questionTitle("Title 3")
                .difficultyLevel("Difficulty 2")
                .option1("a")
                .option2("b")
                .option3("c")
                .option4("d")
                .correctAnswer("a")
                .build();

        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questionsByCategory.add(question2);
        questionsByCategory.add(question3);
        questionsByDifficulty.add(question1);
        questionsByDifficulty.add(question2);
    }

    @Test
    void QuestionController_getAllQuestions_returnsOk() throws Exception{
        when(questionService.getAllQuestions()).thenReturn(questions);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/questions/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void QuestionController_getQuestionsByCategory_returnsOk() throws Exception{
        when(questionService.getQuestionsByCategory("Category 2")).thenReturn(questionsByCategory);

        this.mockMvc.perform(get("/questions/category/Category 2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void QuestionController_getQuestionsByDifficulty_returnsOk() throws Exception{
        when(questionService.getQuestionsByDifficulty("Difficulty 2")).thenReturn(questionsByDifficulty);

        this.mockMvc.perform(get("/questions/difficulty/difficulty 2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void QuestionController_addQuestion_returnsCreated() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(question1);

        when(questionService.addQuestion(question1)).thenReturn("Question added successfully");
        this.mockMvc.perform(post("/questions/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void QuestionController_deleteQuestion_returnsOk() throws Exception{
        when(questionService.deleteQuestion(1L)).thenReturn("Question with id=1 deleted successfully");

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/questions/delete")
                .param("id","1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void QuestionController_updateQuestion_returnsOk() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(question1);

        when(questionService.updateQuestion(question1)).thenReturn("Question updated successfully");
        this.mockMvc.perform(put("/questions/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }
}