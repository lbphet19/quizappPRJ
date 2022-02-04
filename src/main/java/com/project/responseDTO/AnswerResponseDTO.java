package com.project.responseDTO;

import java.util.List;

public class AnswerResponseDTO {
	
	private List<CorrectAnswerResponseDTO> answerResponses;
	private int score;
	public List<CorrectAnswerResponseDTO> getAnswerResponses() {
		return answerResponses;
	}
	public void setAnswerResponses(List<CorrectAnswerResponseDTO> answerResponses) {
		this.answerResponses = answerResponses;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public AnswerResponseDTO(List<CorrectAnswerResponseDTO> answerResponses, int score) {
		super();
		this.answerResponses = answerResponses;
		this.score = score;
	}
	
	
}
