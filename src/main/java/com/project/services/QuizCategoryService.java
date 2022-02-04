package com.project.services;

import java.util.List;

import com.project.entity.QuizCategory;

public interface QuizCategoryService {
	List<QuizCategory> findAll();
	QuizCategory findById(int id);
	QuizCategory save(QuizCategory cate);
	QuizCategory update(QuizCategory updateCat);
	String delete(int id);
}
