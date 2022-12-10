package com.project.responseDTO;

import java.util.List;

import com.project.entity.Exam;

public class CategoryWithExam {
	private int catId;
	private String catName;
	private List<Exam> exams;
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
	

	public CategoryWithExam(int catId, String catName) {
		super();
		this.catId = catId;
		this.catName = catName;
	}
	public CategoryWithExam(int catId, String catName, List<Exam> exams) {
		super();
		this.catId = catId;
		this.catName = catName;
		this.exams = exams;
	}
	public CategoryWithExam() {
		super();
	}
	public List<Exam> getExams() {
		return exams;
	}
	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}
	
	
	
}
