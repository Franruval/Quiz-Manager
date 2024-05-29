package com.ruvalcaba.quizapplication.DAO;

import com.ruvalcaba.quizapplication.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDAO extends JpaRepository<QuestionModel, Integer> {

}
