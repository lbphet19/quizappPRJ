package com.project.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Answer;
import com.project.repositories.AnswerRepository;

@Service

public class AnswerServiceImp implements AnswerService {

	@Autowired
	private AnswerRepository answerRepo;
	@Override
	public Answer save(Answer answer) {
		// TODO Auto-generated method stub
		return answerRepo.save(answer);
	}

	@Override
	public List<Answer> save(Answer[] answers) {
		// TODO Auto-generated method stub
		return Arrays.asList(answers).stream().map(answer -> save(answer))
				.collect(Collectors.toList());
	}

	@Override
	public List<Answer> get() {
		// TODO Auto-generated method stub
		return answerRepo.findAll();
	}

	@Override
	public Answer getById(int answerId) {
		// TODO Auto-generated method stub
		return answerRepo.findById(answerId).get();
	}

	@Override
	public Answer update(Answer updateAnswer) {
		// TODO Auto-generated method stub
		Answer ans = getById(updateAnswer.getAnswerId());
		ans.setAnswerContent(updateAnswer.getAnswerContent());
		ans.setAnswerIsCorrect(updateAnswer.isAnswerIsCorrect());
		answerRepo.save(ans);
		return ans;
	}

	@Override
	public List<Answer> update(Answer[] updateAnswers) {
		// TODO Auto-generated method stub
		return Arrays.asList(updateAnswers).stream().map(answer -> update(answer))
				.collect(Collectors.toList());
	}
	
	@Override
	public String delete(int answerId) {
		try {
			this.answerRepo.deleteById(answerId);
			return "Success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "An error occurred";
	}
	public boolean equalsIngnoreOrder(List<Integer> list1, List<Integer> list2) {
		if((list1.size() == list2.size()) && list1.containsAll(list2) && list2.containsAll(list1))
			return true;
		return false;
	}
	
}
