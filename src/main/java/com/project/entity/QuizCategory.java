package com.project.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Table(name = "QuizCategory")
@Entity
public class QuizCategory {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "categoryName")
	private String categoryName;
	
	@OneToMany(mappedBy = "quizCategory",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Quiz> quizzes = new ArrayList<Quiz>();
	
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	@JoinColumn(name = "parentId" , referencedColumnName = "id")
	private QuizCategory parent;
	
	@OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
	private List<QuizCategory> children;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	public List<QuizCategory> getChildren() {
		return children;
	}

	public void setChildren(List<QuizCategory> children) {
		this.children = children;
	}

	public QuizCategory getParent() {
		return parent;
	}

	public void setParent(QuizCategory parent) {
		this.parent = parent;
	}

	public QuizCategory(String categoryName, List<Quiz> quizzes) {
		super();
		this.categoryName = categoryName;
		this.quizzes = quizzes;
	}

	public QuizCategory() {
		super();
	}
	
	
	
}
