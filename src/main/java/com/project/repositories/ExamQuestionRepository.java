package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entity.ExamQuestion;
import com.project.entity.Question;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Integer> {
	@Query("SELECT q FROM ExamQuestion exQuest JOIN exQuest.exam ex JOIN exQuest.question q "
			+ "WHERE ex.examId = ?1")
	List<Question> getQuestionByExamId(int examId);
	
	@Query("SELECT q.id FROM ExamQuestion exQuest JOIN exQuest.exam ex JOIN exQuest.question q "
			+ "WHERE ex.examId = ?1")
	List<Integer> getExamQuestionIds(int examId);
	
}
