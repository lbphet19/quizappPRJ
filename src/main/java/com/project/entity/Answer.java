package com.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "Answer")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AnswerId")
	private int answerId;
	@Column(name = "AnswerContent")
	private String answerContent;
	@Column(name = "AnswerIsCorrect")
	private boolean answerIsCorrect;
	@ManyToOne
//	@JsonIgnore()
	@JsonProperty(access =Access.WRITE_ONLY )
	@JoinColumn(name = "QuestionId",referencedColumnName = "questionId")
	private Question question;
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public boolean isAnswerIsCorrect() {
		return answerIsCorrect;
	}
	public void setAnswerIsCorrect(boolean answerIsCorrect) {
		this.answerIsCorrect = answerIsCorrect;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Answer(int answerId, String answerContent, boolean answerIsCorrect, Question question) {
		super();
		this.answerId = answerId;
		this.answerContent = answerContent;
		this.answerIsCorrect = answerIsCorrect;
		this.question = question;
	}
	public Answer() {
		super();
	}
	
}
