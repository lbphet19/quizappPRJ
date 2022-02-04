package com.project.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Question;
import com.project.repositories.QuestionRepository;

@Service
public class QuestionServiceImp implements QuestionService {

	@Autowired
	private QuestionRepository questionRepo;
	
	@Override
	public Question save(Question question) {
		// TODO Auto-generated method stub
		return questionRepo.save(question);
	}

	@Override
	public List<Question> save(Question[] questions) {
		// TODO Auto-generated method stub
		return Arrays.asList(questions).stream().map(question -> save(question))
				.collect(Collectors.toList());
	}

	@Override
	public List<Question> get() {
		// TODO Auto-generated method stub
		return questionRepo.findAll();
	}

	@Override
	public List<Question> getByQuizId(int quizId) {
		// TODO Auto-generated method stub
		return questionRepo.findQuestionByQuizId(quizId);
	}

	@Override
	public Question getById(int questionId) {
		// TODO Auto-generated method stub
		return questionRepo.findById(questionId).get();
	}

	@Override
	public Question update(Question updateQuestion) {
		// TODO Auto-generated method stub
		Question question = getById(updateQuestion.getQuestionId());
		question.setQuestionContent(updateQuestion.getQuestionContent());
		question.setQuestionType(updateQuestion.getQuestionType());
//		question.setQuiz(updateQuestion.getQuiz());
//		question.setAnswers(updateQuestion.getAnswers());
		questionRepo.save(question);
		return question;
	}

	@Override
	public List<Question> update(Question[] questions) {
		// TODO Auto-generated method stub
		return Arrays.asList(questions).stream().map(question -> update(question))
				.collect(Collectors.toList());
	}

	@Override
	public String delete(int questionId) {
		// TODO Auto-generated method stub
		try {
			questionRepo.deleteById(questionId);	
			return "Success!";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		return "An error occurred!";
	}

	@Override
	public List<List<Integer>> getAnswerIds(List<Question> questions) {
		// TODO Auto-generated method stub
		List<List<Integer>> answerIds = new ArrayList<List<Integer>>();
		for(Question q: questions) {
			List<Integer> list = q.getAnswers().stream().map(ans -> ans.getAnswerId())
					.sorted().collect(Collectors.toList());
			answerIds.add(list);
		}
		return answerIds;
	}

}
