package com.project.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "username")
	@NotBlank
	@Size(max = 20)
	private String username;

	@Column(name = "email")
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	@NotBlank
	@Column(name = "password")
	private String password;
	
	@Column(name = "name", columnDefinition = "nvarchar(255)")
	private String name;
	
	@OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Quiz> quizzes = new ArrayList<Quiz>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
//many to many voi quiz => favourite quizzes
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_quizzes", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "quiz_id"))
	private Set<Quiz> favouriteQuizList = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<QuizScore> quizScores = new ArrayList<QuizScore>();
	
	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public User(String username, String email,
			 String password, String name) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	public List<QuizScore> getQuizScores() {
		return quizScores;
	}

	public void setQuizScores(List<QuizScore> quizScores) {
		this.quizScores = quizScores;
	}

	public Set<Quiz> getFavouriteQuizList() {
		return favouriteQuizList;
	}

	public void setFavouriteQuizList(Set<Quiz> favouriteQuizList) {
		this.favouriteQuizList = favouriteQuizList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public void addQuizToFavouriteList(Quiz quiz) {
		this.getFavouriteQuizList().add(quiz);
		quiz.getFavouritedUsers().add(this);
	}
	public void removeQuizFromFavouriteList(Quiz quiz) {
		this.getFavouriteQuizList().remove(quiz);
		quiz.getFavouritedUsers().remove(this);
	}
}
