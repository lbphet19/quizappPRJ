package com.project.entity;

public class QuestionWithPosition {
	private int questionId;
	private int position;
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public QuestionWithPosition(int questionId, int position) {
		super();
		this.questionId = questionId;
		this.position = position;
	}
	
}
