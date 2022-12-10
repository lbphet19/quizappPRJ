package com.project.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "UserExamScore")
@Entity
public class UserExamScore {
// user id quiz id score
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examId",referencedColumnName = "examId")
	private Exam exam;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId",referencedColumnName = "id")
	private User user;
	
	@Column(name = "startTime")
	private long startTime;
	
	@Column(name = "endTime")
	private long endTime;
	
	@Column(name = "userScore")
	private String score;
	
	@Column(name = "status")
	private String status;
//	dang lam, da xong...
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public UserExamScore() {
		super();
	}
	public UserExamScore(Exam exam, User user, long startTime, long endTime, String score, String status) {
		super();
		this.exam = exam;
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
		this.score = score;
		this.status = status;
	}

	
	
}
