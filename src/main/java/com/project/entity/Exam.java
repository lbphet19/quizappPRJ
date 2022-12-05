package com.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Exam")
public class Exam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "examId")
	private int examId;
	
	@Column(name = "examName")
	private String examName;
	
	@Column(name = "Descriptions")
	private String descriptions;
	
//	quiz co nhieu question
//	@Column(name = "QuizStatus")
//	private boolean quizStatus;
	
	@Column(name = "shuffleQuestion")
	private boolean shuffleQuestion;
	
	@Column(name = "shuffleAnswer")
	private boolean shuffleAnswer;
	
	@Column(name = "time")
	private long time; // tinh thoi gian = giay -> client render ve HH:mm:ss
	
	
	@Column(name = "examImage")
	private String examImage;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JsonProperty(access = Access.WRITE_ONLY)
//	@JoinColumn(name = "quizId",referencedColumnName = "quizId")
//	private Quiz quiz;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonProperty(access = Access.WRITE_ONLY)
	@JoinColumn(name = "quizCategoryId",referencedColumnName = "id")
	private QuizCategory quizCategory;

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public boolean isShuffleQuestion() {
		return shuffleQuestion;
	}

	public void setShuffleQuestion(boolean shuffleQuestion) {
		this.shuffleQuestion = shuffleQuestion;
	}

	public boolean isShuffleAnswer() {
		return shuffleAnswer;
	}

	public void setShuffleAnswer(boolean shuffleAnswer) {
		this.shuffleAnswer = shuffleAnswer;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getExamImage() {
		return examImage;
	}

	public void setExamImage(String examImage) {
		this.examImage = examImage;
	}



	public QuizCategory getQuizCategory() {
		return quizCategory;
	}

	public void setQuizCategory(QuizCategory quizCategory) {
		this.quizCategory = quizCategory;
	}

	public Exam() {
		super();
	}
	
	
}
