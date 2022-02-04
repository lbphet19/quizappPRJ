package com.project.services;

import java.util.List;

import com.project.entity.Question;
import com.project.entity.Quiz;
import com.project.entity.User;

public interface QuizService {
	Quiz save(Quiz quiz);
	List<Quiz> save(Quiz[] quizzes);
	List<Quiz> get();
	List<Quiz> getUserQuiz(User user);
	List<Quiz> getQuizByCategory(int catId);
//	List<Quiz> getByQuizId(int quizId);
	Quiz getById(int quizId);
	Quiz update(Quiz updateQuiz);
//	List<Quiz> update(Question[] updateQuestions);
	String delete(int quizId);
}
