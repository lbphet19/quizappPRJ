package com.project.responseDTO;

import com.project.entity.Quiz;

public class QuizDetailDTO {
	 private int quizId;
     private String quizName;
     private String descriptions;
     private boolean quizStatus;
     private String quizImage;
     //author
     private int authorId;
     private String authorName;
     //cate
     private int catId;
     private String catName;
     
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

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public boolean isQuizStatus() {
		return quizStatus;
	}

	public void setQuizStatus(boolean quizStatus) {
		this.quizStatus = quizStatus;
	}

	public String getQuizImage() {
		return quizImage;
	}

	public void setQuizImage(String quizImage) {
		this.quizImage = quizImage;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	

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

	public QuizDetailDTO(Quiz quiz) {
		this.quizId = quiz.getQuizId();
		this.quizName = quiz.getQuizName();
		this.descriptions = quiz.getDescriptions();
		this.quizStatus = quiz.isQuizStatus();
		this.quizImage = quiz.getQuizImage();
		this.authorId = quiz.getAuthor().getId();
		this.authorName = quiz.getAuthor().getName();
		this.catId = quiz.getQuizCategory().getId();
		this.authorName = quiz.getQuizCategory().getCategoryName();
	}

}
