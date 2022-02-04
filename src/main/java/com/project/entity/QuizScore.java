package com.project.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "QuizScore")
@Entity
public class QuizScore {
// user id quiz id score
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@ManyToOne
	@JoinColumn(name = "QuizId",referencedColumnName = "quizId")
	private Quiz quiz;
	@ManyToOne
	@JoinColumn(name = "userId",referencedColumnName = "id")
	private User user;
	@Column(name = "quizTime")
	private Date quizTime;
	@Column(name = "userScore")
	private String score;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getQuizTime() {
		return quizTime;
	}
	public void setQuizTime(Date quizTime) {
		this.quizTime = quizTime;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public QuizScore(Quiz quiz, User user, Date quizTime, String score) {
		super();
		this.quiz = quiz;
		this.user = user;
		this.quizTime = quizTime;
		this.score = score;
	}
	
}
