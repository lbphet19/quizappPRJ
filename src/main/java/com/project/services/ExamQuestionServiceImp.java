package com.project.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.ExamQuestion;
import com.project.repositories.ExamQuestionRepository;

@Service
public class ExamQuestionServiceImp implements ExamQuestionService{

	private ExamQuestionRepository examQuestionRepo;
	@Autowired
	public ExamQuestionServiceImp(ExamQuestionRepository exQuesRepo) {
		this.examQuestionRepo = exQuesRepo;
	}
	@Override
	public List<ExamQuestion> findAll() {
		// TODO Auto-generated method stub
		return this.examQuestionRepo.findAll();
	}

	@Override
	public ExamQuestion findById(Integer id) {
		// TODO Auto-generated method stub
		return this.examQuestionRepo.findById(id).get();
	}

	@Override
	public String delete(Integer id) {
		// TODO Auto-generated method stub
		try {
			ExamQuestion exQues = this.examQuestionRepo.findById(id).get();
			this.examQuestionRepo.delete(exQues);
			return "Success!";
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			return "no such element exists!";
		}
	}

	@Override
	public ExamQuestion save(ExamQuestion e) {
		// TODO Auto-generated method stub
		return this.examQuestionRepo.save(e);
	}
	
	public List<ExamQuestion> saveAll(List<ExamQuestion> examQuestions) {
		// TODO Auto-generated method stub
		return this.examQuestionRepo.saveAll(examQuestions);
	}

	@Override
	public ExamQuestion update(ExamQuestion update) {
		// TODO Auto-generated method stub
		return this.examQuestionRepo.save(update);
	}
	
	
}
