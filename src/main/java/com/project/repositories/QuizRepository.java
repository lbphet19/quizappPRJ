package com.project.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entity.Exam;
import com.project.entity.Quiz;
import com.project.responseDTO.QuizWithQuestionResponseDTO;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
//	List<Quiz> getById(int quizId);
	@Query("SELECT q FROM Quiz q WHERE q.author.id = ?1")
	List<Quiz> getUserQuizzes(int userId);
	
	@Query("SELECT q FROM Quiz q WHERE q.quizName LIKE %?1%")
	Page<Quiz> searchQuizByName(String key, Pageable page);
	
	@Query("SELECT q FROM Quiz q WHERE q.quizName LIKE %?1%")
	List<Quiz> searchByName(String key);
	
	@Query("SELECT q FROM Quiz q WHERE q.quizCategory.id= ?1")
	Page<Quiz> getQuizByCatId(int catId,Pageable page);
	
	@Query("SELECT new com.project.responseDTO.QuizWithQuestionResponseDTO(q.quizId,q.quizName,q.questions) "
+ "FROM Quiz q WHERE q.quizCategory.id= ?1")
	List<QuizWithQuestionResponseDTO> getQuizWithQuestionByCatId(int catId);
	
	@Query("SELECT q FROM Quiz q JOIN q.quizCategory cat WHERE cat.id = ?1 AND q.quizId != ?2")
	Page<Quiz> getRelatedQuiz(int catId, int quizId,Pageable page);
	
//	@Query("SELECT ex FROM Exam ex JOIN ex.quiz q WHERE q.quizId = ?1")
//	List<Exam> getQuizExam(int quizId);
}
