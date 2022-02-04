package com.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.QuizCategory;
import com.project.repositories.QuizCategoryRepository;

@Service
public class QuizCategoryServiceImp implements QuizCategoryService {
	
	@Autowired
	private QuizCategoryRepository quizCategoryRepo;
	
	@Override
	public List<QuizCategory> findAll() {
		// TODO Auto-generated method stub
		return this.quizCategoryRepo.findAll();
	}

	@Override
	public QuizCategory findById(int id) {
		// TODO Auto-generated method stub
		return this.quizCategoryRepo.findById(id).get();
	}

	@Override
	public QuizCategory save(QuizCategory cate) {
		// TODO Auto-generated method stub
		return this.quizCategoryRepo.save(cate);
	}

	@Override
	public QuizCategory update(QuizCategory updateCat) {
		// TODO Auto-generated method stub
		QuizCategory cate = this.quizCategoryRepo.findById(updateCat.getId()).get();
		cate.setCategoryName(updateCat.getCategoryName());
		return this.quizCategoryRepo.save(cate);
	}

	@Override
	public String delete(int id) {
		// TODO Auto-generated method stub
		try {
		this.quizCategoryRepo.deleteById(id);
		return "success";
		}catch(Exception e) {
			return "error occurred!";
		}
	}

}
