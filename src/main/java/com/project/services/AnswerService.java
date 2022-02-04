package com.project.services;

import java.util.List;

//import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Service;

import com.project.entity.Answer;

@Service
public interface AnswerService {
	Answer save(Answer answer);
	List<Answer> save(Answer[] answers);
	List<Answer> get();
	Answer getById(int answerId);
	Answer update(Answer updateAnswer);
	List<Answer> update(Answer[] updateAnswers);
	String delete(int answerId);
	boolean equalsIngnoreOrder(List<Integer> list1, List<Integer> list2);
}
