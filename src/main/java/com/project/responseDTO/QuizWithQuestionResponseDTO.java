package com.project.responseDTO;

import java.util.Collection;
import java.util.List;

import com.project.entity.Question;

public class QuizWithQuestionResponseDTO {
	private int quizId;
	private String quizName;
//	private List<Question> questions;
	
	public QuizWithQuestionResponseDTO() {
		
	}

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

//	public List<Question> getQuestions() {
//		return questions;
//	}
//
//	public void setQuestions(List<Question> questions) {
//		this.questions = questions;
//	}

	public QuizWithQuestionResponseDTO(int quizId, String quizName, Collection<Question> questions) {
		super();
		this.quizId = quizId;
		this.quizName = quizName;
//		this.questions = (List<Question>) questions;
	}

public QuizWithQuestionResponseDTO(int quizId, String quizName) {
	super();
	this.quizId = quizId;
	this.quizName = quizName;
}
	
	
	
}
