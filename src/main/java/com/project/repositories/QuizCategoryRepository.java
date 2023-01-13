package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.entity.QuizCategory;
@Repository
public interface QuizCategoryRepository extends JpaRepository<QuizCategory, Integer> {
	@Query("SELECT cat FROM QuizCategory cat WHERE cat.parent != NULL")
	List<QuizCategory> getChildQuizCategory();
	
	@Query("SELECT cat FROM QuizCategory cat WHERE cat.parent = NULL")
	List<QuizCategory> getRootQuizCategory();
	
	@Query("SELECT cat FROM QuizCategory cat WHERE lower(cat.categoryName) LIKE lower(concat('%',:search,'%'))  AND cat.parent != NULL")
	List<QuizCategory> search(@Param("search") String search);
	
	
//	@Query("SELECT cat FROM QuizCategory cat WHERE cat.id = ?1")
//	List<QuizCategory> getByIdWithExam(int id);
}
