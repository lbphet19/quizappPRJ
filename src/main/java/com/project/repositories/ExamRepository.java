package com.project.repositories;


import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entity.Exam;
import com.project.entity.Quiz;
import com.project.responseDTO.QuizWithQuestionResponseDTO;
import com.project.responseDTO.QuizWithQuestionsDTO;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
	@Query("SELECT ex FROM Exam ex JOIN ex.quizCategory cat WHERE cat.id = ?1")
	List<Exam> getExamByCategoryId(int catId);
	
	@Query("SELECT ex FROM Exam ex JOIN ex.quizCategory cat WHERE cat.id = ?1")
	Page<Exam> getExamByCategoryIdPagination(int catId,Pageable page);
	
	
	@Query("SELECT new com.project.responseDTO.QuizWithQuestionsDTO(q) FROM "
			+ "Quiz q JOIN q.quizCategory cat WHERE cat.id =?1")
	Set<QuizWithQuestionsDTO> getQuestionsByCatId(int catId);
	
	
	@Query("SELECT q FROM "
			+ "Quiz q LEFT JOIN q.questions JOIN q.quizCategory cat WHERE cat.id =?1")
	List<Quiz> getTest(int catId);
	
	@Query("SELECT cat.id FROM Exam ex JOIN ex.quizCategory cat WHERE ex.examId=?1")
	Integer getExamCategoryId(int examId);
}
