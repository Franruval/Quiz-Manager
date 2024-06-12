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

    @BeforeEach
    void setUp(){

        questionModel1 = QuestionModel.builder()
                .category("Category 1").questionTitle("Title 1").difficultyLevel("Difficulty 1")
                .option1("Option 1").option2("Option 2").option3("Option 3").option4("Option 4")
                .correctAnswer("Option 1").build();
        questionModel2 = QuestionModel.builder()
                .category("Category 1").questionTitle("Title 2").difficultyLevel("Difficulty 1")
                .option1("Option 1").option2("Option 2").option3("Option 3").option4("Option 4")
                .correctAnswer("Option 1").build();
        questionModel3 = QuestionModel.builder()
                .category("Category 2").questionTitle("Title 3").difficultyLevel("Difficulty 3")
                .option1("Option 1").option2("Option 2").option3("Option 3").option4("Option 4")
                .correctAnswer("Option 1").build();


        questionRepo.save(questionModel1);
        questionRepo.save(questionModel2);
        questionRepo.save(questionModel3);
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
}
