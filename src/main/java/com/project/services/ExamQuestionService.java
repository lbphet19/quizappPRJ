package com.project.services;

import java.util.List;

import com.project.entity.ExamQuestion;

public interface ExamQuestionService extends BaseService<ExamQuestion, Integer> {
	List<ExamQuestion> saveAll(List<ExamQuestion> examQuestions);
}
