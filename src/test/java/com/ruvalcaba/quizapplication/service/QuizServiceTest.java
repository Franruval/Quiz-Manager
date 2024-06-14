package com.ruvalcaba.quizapplication.service;

import com.ruvalcaba.quizapplication.exception.QuestionNotFoundException;
import com.ruvalcaba.quizapplication.exception.QuizInvalidSizeException;
import com.ruvalcaba.quizapplication.exception.QuizNotFoundException;
import com.ruvalcaba.quizapplication.model.Answer;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.model.QuestionModelWrapper;
import com.ruvalcaba.quizapplication.model.QuizModel;
import com.ruvalcaba.quizapplication.repository.QuestionRepo;
import com.ruvalcaba.quizapplication.repository.QuizRepo;
import com.ruvalcaba.quizapplication.service.implementation.QuizServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    QuestionRepo questionRepo;
    @Mock
    QuizRepo quizRepo;
    @InjectMocks
    QuizServiceImpl quizService;

    QuestionModel question1;
    QuestionModel question2;
    QuestionModel question3;
    QuizModel quiz;
    AutoCloseable autoCloseable;
    List<QuestionModel> questionsByCategory = new ArrayList<>();

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);

        question1 = QuestionModel.builder()
                .category("Category 2")
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
                .difficultyLevel("Difficulty 3")
                .option1("a")
                .option2("b")
                .option3("c")
                .option4("d")
                .correctAnswer("a")
                .build();

        questionsByCategory.add(question1);
        questionsByCategory.add(question2);
        questionsByCategory.add(question3);

        quiz = QuizModel.builder()
                .quizTitle("Test title")
                .questions(questionsByCategory)
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
        quiz = null;
        questionsByCategory = null;
        question1 = question2 = question3 = null;
    }

    @Test
    void QuizService_createQuiz_returnsSuccessMessage(){
        // Given
        String category = "Category 2";
        int totalQuestions = 2;
        if(totalQuestions > questionsByCategory.size())
            totalQuestions=questionsByCategory.size();
        List<QuestionModel> randomQuestions = new ArrayList<>(questionsByCategory);
        Collections.shuffle(randomQuestions);
        randomQuestions = randomQuestions.subList(0,totalQuestions);
        quiz.setQuestions(randomQuestions);


        // When
        when(questionRepo.findByCategory(category)).thenReturn(questionsByCategory);
        when(questionRepo.findRandomQuestionsByCategory(category,totalQuestions)).thenReturn(randomQuestions);
        when(quizRepo.save(quiz)).thenReturn(quiz);

        // Then
        assertThat(quizService.createQuiz(category, totalQuestions, "Test title")).isEqualTo("Success");
    }

    @Test
    void QuizService_createQuiz_throwsQuestionNotFoundException(){
        // Given
        String category = "Category 3";
        int totalQuestions = 2;
        String quizTitle = "Test Quiz";


        // When
        when(questionRepo.findByCategory(category)).thenReturn(new ArrayList<>());

        // Then
        Exception e = assertThrows(QuestionNotFoundException.class, () -> {
            quizService.createQuiz("Category 3",totalQuestions, quizTitle);
        });

        String expectedMessage = "No questions with the specified category were found";
        String actualMessage = e.getMessage();

        assertThat(actualMessage.contains(expectedMessage)).isTrue();
    }

    @Test
    void QuizService_createQuiz_throwsQuizInvalidSizeException(){
        // Given
        String category = "Category 2";
        int totalQuestions = 0;
        String quizTitle = "Test Quiz";


        // When
        when(questionRepo.findByCategory(category)).thenReturn(questionsByCategory);

        // Then
        Exception e = assertThrows(QuizInvalidSizeException.class, () -> {
            quizService.createQuiz("Category 2",totalQuestions, quizTitle);
        });

        String expectedMessage = "The quiz cannot be of size 0 or less";
        String actualMessage = e.getMessage();

        assertThat(actualMessage.contains(expectedMessage)).isTrue();
    }

    @Test
    void QuizService_getQuiz_returnsListOfQuestionModelWrapper(){
        // Given
        Long id=1L;
        List<QuestionModelWrapper> questionsDTO = new ArrayList<>();
        questionsDTO = questionsByCategory.stream()
                .map(dto -> new QuestionModelWrapper(dto.getId(), dto.getQuestionTitle(), dto.getOption1()
                ,dto.getOption2(), dto.getOption3(), dto.getOption4()))
                .collect(Collectors.toList());

        // When
        when(quizRepo.findById(id)).thenReturn(Optional.ofNullable(quiz));

        // Then
        assertThat(quizService.getQuiz(id).get(0).getQuestionTitle()).isEqualTo(questionsByCategory.get(0).getQuestionTitle());
        assertThat(quizService.getQuiz(id)).isEqualTo(questionsDTO);
    }

    @Test
    void QuizService_getQuiz_throwsQuizNotFoundException(){

        when(quizRepo.findById(1L).isEmpty()).thenThrow(new QuizNotFoundException("No quiz with such ID was found"));

        // Then
        Exception e = assertThrows(QuizNotFoundException.class, () -> {
            quizService.getQuiz(1L);
        });

        String expectedMessage = "No quiz with such ID was found";
        String actualMessage = e.getMessage();

        assertThat(actualMessage.contains(expectedMessage)).isTrue();
    }

    @Test
    void QuizService_calculateScore_returnsStringWithObtainedScore(){
        // Given
        Long id = 1L;
        List<Answer> answers = new ArrayList<>();
        Answer answer1 = new Answer(questionsByCategory.get(0).getId(),"a");
        Answer answer2 = new Answer(questionsByCategory.get(1).getId(),"b");
        Answer answer3 = new Answer(questionsByCategory.get(2).getId(),"c");
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);

        List<Answer> answers2 = new ArrayList<>();
        Answer answer4 = new Answer(questionsByCategory.get(0).getId(),"b");
        Answer answer5 = new Answer(questionsByCategory.get(1).getId(),"a");
        Answer answer6 = new Answer(questionsByCategory.get(2).getId(),"a");
        answers2.add(answer4);
        answers2.add(answer5);
        answers2.add(answer6);

        // When
        when(quizRepo.findById(id)).thenReturn(Optional.ofNullable(quiz));

        // Then
        assertThat(quizService.calculateScore(id,answers)).isEqualTo(1);
        assertThat(quizService.calculateScore(id,answers2)).isEqualTo(2);
    }

    @Test
    void QuizService_calculateScore_throwsQuizNotFoundException(){

        when(quizRepo.findById(1L)).thenReturn(Optional.of(new QuizModel()));
        when(quizRepo.findById(1L).isEmpty()).thenThrow(new QuizNotFoundException("No quiz with such ID was found"));

        // Then
        Exception e = assertThrows(QuizNotFoundException.class, () -> {
            quizService.calculateScore(1L, new ArrayList<>());
        });

        String expectedMessage = "No quiz with such ID was found";
        String actualMessage = e.getMessage();

        assertThat(actualMessage.contains(expectedMessage)).isTrue();
    }
}
