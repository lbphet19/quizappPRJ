package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
//	@Query(value = "SELECT ")
//	@Query(value = "SELECT ans FROM Answer ans WHERE ans.answerId = 1")
//	List<Answer> getCorrectAnswers();
	@Query(value = "SELECT q.questionId,a FROM Answer a JOIN a.question q WHERE q.questionId = ?1"
			+ " AND a.answerIsCorrect = true")
	List<Object> test(int questionId);

}
