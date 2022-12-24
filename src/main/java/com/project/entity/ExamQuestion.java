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
	
	@Column(name="position")
	private int position;
	
	
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
	
	
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public ExamQuestion() {
		super();
	}
	public ExamQuestion(Question question, Exam exam) {
		super();
		this.question = question;
		this.exam = exam;
	}
	public ExamQuestion(Question question, Exam exam, int position) {
		super();
		this.question = question;
		this.exam = exam;
		this.position = position;
	}
	
	
	
}
