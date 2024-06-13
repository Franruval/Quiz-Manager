package com.ruvalcaba.quizapplication.repository;

import com.ruvalcaba.quizapplication.model.QuestionModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class QuestionRepoTest {

    @Autowired
    private QuestionRepo questionRepo;
    QuestionModel questionModel1;
    QuestionModel questionModel2;
    QuestionModel questionModel3;
    QuestionModel questionModel4;
    QuestionModel questionModel5;
    QuestionModel questionModel6;

    @BeforeEach
    void setUp(){
        questionModel1 = QuestionModel.builder()
                .category("Category 1")
                .questionTitle("Title 1")
                .difficultyLevel("Difficulty 1")
                .build();
        questionModel2 = QuestionModel.builder()
                .category("Category 1")
                .questionTitle("Title 2")
                .difficultyLevel("Difficulty 1")
                .build();
        questionModel3 = QuestionModel.builder()
                .category("Category 2")
                .questionTitle("Title 3")
                .difficultyLevel("Difficulty 3")
                .build();
        questionModel4 = QuestionModel.builder()
                .category("Category 2")
                .questionTitle("Title 4")
                .difficultyLevel("Difficulty 3")
                .build();
        questionModel5 = QuestionModel.builder()
                .category("Category 2")
                .questionTitle("Title 5")
                .difficultyLevel("Difficulty 4")
                .build();
        questionModel6 = QuestionModel.builder()
                .category("Category 2")
                .questionTitle("Title 6")
                .difficultyLevel("Difficulty 4")
                .build();

        questionRepo.save(questionModel1);
        questionRepo.save(questionModel2);
        questionRepo.save(questionModel3);
        questionRepo.save(questionModel4);
        questionRepo.save(questionModel5);
        questionRepo.save(questionModel6);
    }

    @AfterEach
    void tearDown(){
        questionModel1 = null;
        questionModel2 = null;
        questionModel3 = null;
        questionRepo.deleteAll();
    }

    @Test
    public void QuestionRepo_FindByCategory_ReturnListOfQuestions(){

        List<QuestionModel> questions = questionRepo.findByCategory("Category 1");
        Assertions.assertThat(questions.get(0).getId()).isEqualTo(questionModel1.getId());
        Assertions.assertThat(questions.get(0).getCategory()).isEqualTo(questionModel1.getCategory());
        Assertions.assertThat(questions.size()).isEqualTo(2);
    }

    @Test
    public void QuestionRepo_FindByDifficultyLevel_ReturnListOfQuestions(){

        List<QuestionModel> questions = questionRepo.findByDifficultyLevel("Difficulty 3");
        Assertions.assertThat(questions.get(0).getId()).isEqualTo(questionModel3.getId());
        Assertions.assertThat(questions.get(1).getDifficultyLevel()).isEqualTo(questionModel4.getDifficultyLevel());
        Assertions.assertThat(questions.size()).isEqualTo(2);
    }

    @Test
    public void QuestionRepo_FindByRandomQuestionsByCategory_ReturnListOfQuestions(){

        List<QuestionModel> questions = questionRepo.findRandomQuestionsByCategory("Category 2", 3);
        Assertions.assertThat(questions.get(1).getCategory()).isEqualTo("Category 2");
        Assertions.assertThat(questions.size()).isEqualTo(3);
    }
}
