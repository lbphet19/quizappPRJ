package com.project.requestDTO;

import java.util.List;

public class ExamQuestionRequestDTO {
  //{examId:id,questionId:[1,2,3]
	private int examId;
	private List<Integer> questionId;
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public List<Integer> getQuestionId() {
		return questionId;
	}
	public void setQuestionId(List<Integer> questionId) {
		this.questionId = questionId;
	}
	
}
