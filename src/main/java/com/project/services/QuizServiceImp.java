package com.project.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Quiz;
import com.project.entity.QuizCategory;
import com.project.entity.User;
import com.project.repositories.QuizCategoryRepository;
import com.project.repositories.QuizRepository;

@Service
public class QuizServiceImp implements QuizService{

	@Autowired
	private QuizRepository quizRepo;
	
	@Autowired
	private QuizCategoryRepository quizCateRepo;
//	@Autowired
//	private User;
	@Override
	public Quiz save(Quiz quiz) {
		// TODO Auto-generated method stub
		return quizRepo.save(quiz);
	}

	@Override
	public List<Quiz> save(Quiz[] quizzes) {
		// TODO Auto-generated method stub
		return Arrays.asList(quizzes).stream().map(quiz -> save(quiz))
				.collect(Collectors.toList());
	}

	@Override
	public List<Quiz> get() {
		// TODO Auto-generated method stub
		return quizRepo.findAll();
	}
	
	public List<Quiz> getUserQuiz(User user) {
		// TODO Auto-generated method stub
		return user.getQuizzes();
	}
	
	public List<Quiz> getQuizByCategory(int catId) {
		// TODO Auto-generated method stub
		QuizCategory quizCate = this.quizCateRepo.findById(catId).get();
		return quizCate.getQuizzes();
	}

	@Override
	public Quiz getById(int quizId) {
		// TODO Auto-generated method stub
		return quizRepo.findById(quizId).get();
	}

	@Override
	public Quiz update(Quiz updateQuiz) {
		// TODO Auto-generated method stub
		Quiz quiz = quizRepo.findById(updateQuiz.getQuizId()).get();
		quiz.setQuizName(updateQuiz.getQuizName());
		quiz.setQuizStatus(updateQuiz.isQuizStatus());
		quiz.setDescriptions(updateQuiz.getDescriptions());
//		quiz.setQuizCategory(updateQuiz.getQuizCategory());
//		quiz.setAuthor(updateQuiz.getAuthor());
		
		return quizRepo.save(quiz);
	}

	@Override
	public String delete(int quizId) {
		// TODO Auto-generated method stub
		try {
			quizRepo.deleteById(quizId);
			return "Success!";
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "An error occurred!";
	}

}
