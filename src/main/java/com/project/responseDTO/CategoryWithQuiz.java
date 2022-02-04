package com.project.responseDTO;

import java.util.List;

import com.project.entity.Quiz;
import com.project.entity.QuizCatDTO;

public class CategoryWithQuiz {
	private int catId;
	private String catName;
	private List<QuizCatDTO> quizzes;
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	
	public List<QuizCatDTO> getQuizzes() {
		return quizzes;
	}
	public void setQuizzes(List<QuizCatDTO> quizzes) {
		this.quizzes = quizzes;
	}
	public CategoryWithQuiz(int catId, String catName) {
		super();
		this.catId = catId;
		this.catName = catName;
	}
	public CategoryWithQuiz(int catId, String catName, List<QuizCatDTO> quizzes) {
		super();
		this.catId = catId;
		this.catName = catName;
		this.quizzes = quizzes;
	}
	public CategoryWithQuiz() {
		super();
	}
	
	
	
}
