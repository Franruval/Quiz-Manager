package com.ruvalcaba.quizapplication.DAO;

import com.ruvalcaba.quizapplication.model.QuizModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDAO extends JpaRepository<QuizModel, Integer> {
}
