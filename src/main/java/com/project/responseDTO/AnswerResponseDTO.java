package com.project.responseDTO;

import java.util.List;

public class AnswerResponseDTO {
	
	private List<CorrectAnswerResponseDTO> answerResponses;
	private String score;
	public List<CorrectAnswerResponseDTO> getAnswerResponses() {
		return answerResponses;
	}
	public void setAnswerResponses(List<CorrectAnswerResponseDTO> answerResponses) {
		this.answerResponses = answerResponses;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public AnswerResponseDTO(List<CorrectAnswerResponseDTO> answerResponses, String score) {
		super();
		this.answerResponses = answerResponses;
		this.score = score;
	}
	
	
}
