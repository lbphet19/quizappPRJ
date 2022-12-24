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
//	@Query(nativeQuery = true, value = "select * from question JOIN examquestion eq ON eq.questionid = question.questionid"
//			+ " WHERE eq.examid = 1")
	List<Question> getQuestionByExamId(int examId);
	
	
	@Query(value="select questionid FROM examquestion WHERE examid = ?1 ORDER BY position",nativeQuery = true)
	List<Integer> getExamQuestionIds(int examId);
	
	@Query(nativeQuery = true, value = "select examquestionid from examquestion WHERE examid = ?2 AND questionid IN ?1")
	List<Integer> getExamQuestionIdsByQuestionId(Collection<Integer> questionIds, int examId);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "DELETE FROM examquestion"
			+ " WHERE examid = ?1 AND questionid IN ?2")
	int deleteExQuesById(int examId, Collection<Integer> questionIds);
}
