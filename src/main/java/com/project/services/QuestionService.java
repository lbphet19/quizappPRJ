package com.project.services;

import java.util.List;

import com.project.entity.Answer;
import com.project.entity.Question;

public interface QuestionService {
	Question save(Question question);
	List<Question> save(Question[] questions);
	List<Question> get();
	List<Question> getByQuizId(int quizId);
	Question getById(int questionId);
	Question update(Question updateQuestion);
	List<Question> update(Question[] updateQuestions);
	String delete(int questionId);
	List<List<Integer>> getAnswerIds(List<Question> questions);
}
