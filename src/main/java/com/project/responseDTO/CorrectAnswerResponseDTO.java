package com.project.responseDTO;

import java.util.Collection;
import java.util.List;

import com.project.entity.Answer;
import com.project.entity.Question;

public class CorrectAnswerResponseDTO {
	private boolean isTrue;
	private List<Integer> correctAnswerIds;
	private int questionId;
	
	public List<Integer> getCorrectAnswerIds() {
		return correctAnswerIds;
	}
	public void setCorrectAnswerIds(List<Integer> correctAnswerIds) {
		this.correctAnswerIds = correctAnswerIds;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public boolean isTrue() {
		return isTrue;
	}
	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}
	public CorrectAnswerResponseDTO(boolean isTrue, List<Integer> correctAnswerIds, int questionId) {
		super();
		this.isTrue = isTrue;
		this.correctAnswerIds = correctAnswerIds;
		this.questionId = questionId;
	}
	public CorrectAnswerResponseDTO() {
		super();
	}
	
	
}
