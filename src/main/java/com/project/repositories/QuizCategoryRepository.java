package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.QuizCategory;
@Repository
public interface QuizCategoryRepository extends JpaRepository<QuizCategory, Integer> {

}
