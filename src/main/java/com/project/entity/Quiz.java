package com.project.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "Quiz")
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QuizId")
	private int quizId;
	
	@Column(name = "QuizName")
	private String quizName;
	
	@Column(name = "Descriptions")
	private String descriptions;
	
//	quiz co nhieu question
	@Column(name = "QuizStatus")
	private boolean quizStatus;
	
	@Column(name = "QuizImage")
	private String quizImage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonProperty(access = Access.WRITE_ONLY)
	@JoinColumn(name = "categoryId" , referencedColumnName = "id")
	private QuizCategory quizCategory;
	
	
	//author -> user tao ra quiz
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authorId", referencedColumnName = "id")
	@JsonIgnore
	private User author;
	
	@JsonProperty(access = Access.WRITE_ONLY) 
	@OneToMany(mappedBy = "quiz",fetch = FetchType.LAZY)
	private List<Question> questions = new ArrayList<Question>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "quiz",fetch = FetchType.LAZY)
	private List<QuizScore> quizScores = new ArrayList<QuizScore>();
	
	@ManyToMany(mappedBy = "favouriteQuizList",fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<User> favouritedUsers = new HashSet<User>();
	
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
	
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
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
	public Set<User> getFavouritedUsers() {
		return favouritedUsers;
	}
	public void setFavouritedUsers(Set<User> favouritedUsers) {
		this.favouritedUsers = favouritedUsers;
	}
	public Quiz(int quizId, String quizName, List<Question> questions, boolean quizStatus) {
		super();
		this.quizId = quizId;
		this.quizName = quizName;
		this.questions = questions;
		this.quizStatus = quizStatus;
	}
	
	public Quiz(int quizId, String quizName, String descriptions, boolean quizStatus, List<Question> questions) {
		super();
		this.quizId = quizId;
		this.quizName = quizName;
		this.descriptions = descriptions;
		this.quizStatus = quizStatus;
		this.questions = questions;
	}
	public Quiz() {
		super();
	}
	public QuizCategory getQuizCategory() {
		return quizCategory;
	}
	public void setQuizCategory(QuizCategory quizCategory) {
		this.quizCategory = quizCategory;
	}
	public Quiz(String quizName, String descriptions, boolean quizStatus, QuizCategory quizCategory) {
		super();
		this.quizName = quizName;
		this.descriptions = descriptions;
		this.quizStatus = quizStatus;
		this.quizCategory = quizCategory;
	}
	
}
