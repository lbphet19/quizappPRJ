package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entity.Answer;
import com.project.entity.Question;
import com.project.responseDTO.CorrectAnswerResponseDTO;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	@Query(value = "SELECT q FROM Question q" + " WHERE q.quiz.quizId = ?1")
	List<Question> findQuestionByQuizId(int quizId);

	// test
	@Query(value = "SELECT ans FROM Question q JOIN q.answers ans WHERE q.quiz.quizId = ?1")
	List<Answer> findCorrectAnswers(int quizId);

	@Query(value = "SELECT DISTINCT q" + " FROM Question q" + " JOIN FETCH q.answers ans"
			+ " WHERE q.quiz.quizId = ?1 AND ans.answerIsCorrect = true" + " ORDER BY q.questionId")
	List<Question> getCorrectAnswers(int quizId);

}
