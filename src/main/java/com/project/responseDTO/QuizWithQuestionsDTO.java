package com.project.responseDTO;

import java.util.Collection;

import com.project.entity.Question;
import com.project.entity.Quiz;

public class QuizWithQuestionsDTO {
	private int quizId;
	private String quizName;
	private Collection<Question> questions;
	
	
	
	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public Collection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}

	public QuizWithQuestionsDTO() {
		super();
	}

	public QuizWithQuestionsDTO(Quiz quiz) {
		super();
		this.quizId = quiz.getQuizId();
		this.quizName = quiz.getQuizName();
		this.questions = quiz.getQuestions();
	}
	
	
}
