package com.project.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Exam;
import com.project.repositories.ExamRepository;

@Service
public class ExamServiceImp implements ExamService {
	
	private ExamRepository examRepo;
	@Autowired
	public ExamServiceImp(ExamRepository examRepo) {
		this.examRepo = examRepo;
	}
	@Override
	public List<Exam> findAll() {
		// TODO Auto-generated method stub
		return examRepo.findAll();
	}

	@Override
	public Exam findById(Integer id) {
		// TODO Auto-generated method stub
		return examRepo.findById(id).get();
	}

	@Override
	public String delete(Integer id) {
		// TODO Auto-generated method stub
		try {
		Exam exam = examRepo.findById(id).get();
		examRepo.delete(null);
		return "success";
		}catch (NoSuchElementException e) {
			// TODO: handle exception
			return "no such element exists!";
		}
	}

	@Override
	public Exam save(Exam ex) {
		// TODO Auto-generated method stub
		return this.examRepo.save(ex);
	}

	@Override
	public Exam update(Exam updateEx) {
		// TODO Auto-generated method stub
		Exam oldExam = this.examRepo.findById(updateEx.getExamId()).get();
		oldExam.setExamName(updateEx.getExamName());
		oldExam.setDescriptions(updateEx.getDescriptions());
		oldExam.setExamImage(updateEx.getExamImage());
		return this.examRepo.save(oldExam);
	}

}
