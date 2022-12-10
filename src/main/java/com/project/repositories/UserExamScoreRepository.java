package com.project.repositories;


import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entity.UserExamScore;

@Repository
public interface UserExamScoreRepository extends JpaRepository<UserExamScore, Integer> {
	@Query("SELECT ues FROM UserExamScore ues JOIN ues.exam ex JOIN ues.user user "
			+ "WHERE ex.examId = ?1 AND user.id = ?2 AND ues.status = 'proccessing' ORDER BY ues.startTime DESC")
	List<UserExamScore> findLastAttempt(int examId, int userId, Pageable page);
	default List<UserExamScore> findUserLastAttempt(int examId, int userId) {
	    return findLastAttempt(examId, userId, PageRequest.of(0,1));
	 }
}
