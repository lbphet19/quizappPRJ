package com.project.requestDTO;

import java.util.List;

import com.project.entity.Answer;

public class AnswerRequestDTO {
	//{questionId:1, answers:[1,2,3...]}
	private int questionId;
	private List<Integer> answerIds;
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public List<Integer> getAnswerIds() {
		return answerIds;
	}
	public void setAnswerIds(List<Integer> answerIds) {
		this.answerIds = answerIds;
	}
	public AnswerRequestDTO(int questionId, List<Integer> answers) {
		super();
		this.questionId = questionId;
		this.answerIds = answers;
	}
	public AnswerRequestDTO() {
		super();
	}
	
	
	//questionId
//	answers[]: answerIds...
}
