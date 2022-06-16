package com.project.entity;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "ExamQuestion")
public class ExamQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "examQuestionId")
	private int examQuestionId;
	
	@ManyToOne
	@JoinColumn(name = "questionId",referencedColumnName = "questionId")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "examId",referencedColumnName = "examId")
	private Exam exam;
//	quiz co nhieu question
	@Column(name = "score")
	private int score;
	
	
	public int getExamQuestionId() {
		return examQuestionId;
	}
	public void setExamQuestionId(int examQuestionId) {
		this.examQuestionId = examQuestionId;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public ExamQuestion() {
		super();
	}
	
	
	
}
