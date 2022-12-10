package com.project.responseDTO;

import java.util.List;

import com.project.entity.Question;

public class ExamWithTimeAndQuestionsDTO {

	private long countdown;
	private List<Question> questions;
	public long getCountdown() {
		return countdown;
	}
	public void setCountdown(long countdown) {
		this.countdown = countdown;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public ExamWithTimeAndQuestionsDTO(long countdown, List<Question> questions) {
		super();
		this.countdown = countdown;
		this.questions = questions;
	}
	
	
}
