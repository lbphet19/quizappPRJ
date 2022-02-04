package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.QuizScore;

public interface QuizScoreRepository extends JpaRepository<QuizScore,Integer> {

}
