package com.project.repositories;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "DELETE FROM examquestion"
			+ " WHERE examid = ?1 AND questionid IN ?2")
	int deleteExQuesById(int examId, Collection<Integer> questionIds);
}
