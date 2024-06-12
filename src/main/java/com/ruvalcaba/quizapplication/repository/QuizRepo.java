package com.ruvalcaba.quizapplication.repository;

import com.ruvalcaba.quizapplication.model.QuizModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepo extends JpaRepository<QuizModel, Long> {
}
