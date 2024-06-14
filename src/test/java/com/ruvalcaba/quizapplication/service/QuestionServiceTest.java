package com.ruvalcaba.quizapplication.service;

import com.ruvalcaba.quizapplication.exception.QuestionInvalidFormatException;
import com.ruvalcaba.quizapplication.exception.QuestionNotFoundException;
import com.ruvalcaba.quizapplication.model.QuestionModel;
import com.ruvalcaba.quizapplication.repository.QuestionRepo;
import com.ruvalcaba.quizapplication.service.implementation.QuestionServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    QuestionRepo questionRepo;
    @InjectMocks
    QuestionServiceImpl questionService;

    QuestionModel question1;
    QuestionModel question2;
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);

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
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void QuestionService_getAllQuestions_returnsListOfAllQuestions(){
        // Given
        List<QuestionModel> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);

        // When
        when(questionRepo.findAll()).thenReturn(questions);

        // Then
        assertThat(questionService.getAllQuestions().get(0).getQuestionTitle()).isEqualTo(question1.getQuestionTitle());
        assertThat(questionService.getAllQuestions().size()).isEqualTo(2);
    }

    @Test
    void QuestionService_getQuestionsByCategory_returnsListOfQuestionsFilteredByCategory(){
        // Given
        List<QuestionModel> questionsCat1 = new ArrayList<>();
        questionsCat1.add(question1);

        List<QuestionModel> questionsCat2 = new ArrayList<>();
        questionsCat2.add(question2);

        // When
        when(questionRepo.findByCategory("Category 1")).thenReturn(questionsCat1);
        when(questionRepo.findByCategory("Category 2")).thenReturn(questionsCat2);

        // Then
        assertThat(questionService.getQuestionsByCategory("Category 1").get(0).getQuestionTitle())
                .isEqualTo(question1.getQuestionTitle());
        assertThat(questionService.getQuestionsByCategory("Category 2").get(0).getQuestionTitle())
                .isEqualTo(question2.getQuestionTitle());
        assertThat(questionService.getQuestionsByCategory("Category 1").size()).isEqualTo(1);
        assertThat(questionService.getQuestionsByCategory("Category 2").size()).isEqualTo(1);
    }

    @Test
    void QuestionService_getQuestionsByCategory_throwsQuestionNotFoundException(){
        List<QuestionModel> noQuestions = new ArrayList<>();

        // When
        when(questionRepo.findByCategory("Category 1")).thenReturn(noQuestions);

        Exception e = assertThrows(QuestionNotFoundException.class, () -> {
            questionService.getQuestionsByCategory("Category 1");
        });

        String expectedMessage = "No questions match the specified category";
        String actualMessage = e.getMessage();

        assertThat(actualMessage.contains(expectedMessage)).isTrue();
    }

    @Test
    void QuestionService_getQuestionsByDifficulty_returnsListOfQuestionsFilteredByDifficulty(){
        // Given
        List<QuestionModel> questionsDif1 = new ArrayList<>();
        questionsDif1.add(question1);

        List<QuestionModel> questionsDif2 = new ArrayList<>();
        questionsDif2.add(question2);

        // When
        when(questionRepo.findByDifficultyLevel("Difficulty 1")).thenReturn(questionsDif1);
        when(questionRepo.findByDifficultyLevel("Difficulty 2")).thenReturn(questionsDif2);

        // Then
        assertThat(questionService.getQuestionsByDifficulty("Difficulty 1").get(0).getQuestionTitle())
                .isEqualTo(question1.getQuestionTitle());
        assertThat(questionService.getQuestionsByDifficulty("Difficulty 2").get(0).getQuestionTitle())
                .isEqualTo(question2.getQuestionTitle());
        assertThat(questionService.getQuestionsByDifficulty("Difficulty 1").size()).isEqualTo(1);
        assertThat(questionService.getQuestionsByDifficulty("Difficulty 2").size()).isEqualTo(1);
    }

    @Test
    void QuestionService_getQuestionsByDifficulty_throwsQuestionNotFoundException(){
        List<QuestionModel> noQuestions = new ArrayList<>();

        // When
        when(questionRepo.findByDifficultyLevel("Difficulty 1")).thenReturn(noQuestions);

        Exception e = assertThrows(QuestionNotFoundException.class, () -> {
            questionService.getQuestionsByDifficulty("Difficulty 1");
        });

        String expectedMessage = "No questions match the specified difficulty";
        String actualMessage = e.getMessage();

        assertThat(actualMessage.contains(expectedMessage)).isTrue();
    }

    @Test
    void QuestionService_addQuestion_returnsSuccessMessage(){
        when(questionRepo.save(question1)).thenReturn(question1);
        assertThat(questionService.addQuestion(question1)).isEqualTo("Question added successfully");
        verify(questionRepo, times(1)).save(question1);
    }

    @Test
    void QuestionService_addQuestion_throwsQuestionInvalidFormatException(){
        QuestionModel invalidQuestion = QuestionModel.builder().
                questionTitle("Test Title")
                .build();

        Exception e = assertThrows(QuestionInvalidFormatException.class, () -> {
            questionService.addQuestion(invalidQuestion);
        });

        String expectedMessage = "Invalid format, some fields are null";
        String actualMessage = e.getMessage();

        assertThat(actualMessage.contains(expectedMessage)).isTrue();
        verify(questionRepo, times(0)).save(invalidQuestion);
    }

    /*@Test
    void QuestionService_deleteQuestion_returnsSuccessMessage(){
        mock(QuestionRepo.class, Mockito.CALLS_REAL_METHODS);

        doAnswer(Answers.CALLS_REAL_METHODS).when(questionRepo).deleteById(1L);

        assertThat(questionService.deleteQuestion(1L)).isEqualTo("Question with id=" + 1L + " deleted successfully");
    }*/

    @Test
    void QuestionService_updateQuestion_returnsSuccessMessage(){
        question1.setId(1L);

        when(questionRepo.save(question1)).thenReturn(question1);

        assertThat(questionService.updateQuestion(question1)).isEqualTo("Question updated successfully");
        verify(questionRepo, times(1)).save(question1);
    }

    @Test
    void QuestionService_updateQuestion_throwsQuestionInvalidFormatException(){
        QuestionModel invalidQuestion = QuestionModel.builder().
                questionTitle("Test Title")
                .build();

        Exception e = assertThrows(QuestionInvalidFormatException.class, () -> {
            questionService.updateQuestion(invalidQuestion);
        });

        String expectedMessage = "Invalid format, some fields are null";
        String actualMessage = e.getMessage();

        assertThat(actualMessage.contains(expectedMessage)).isTrue();
        verify(questionRepo, times(0)).save(invalidQuestion);
    }
}
