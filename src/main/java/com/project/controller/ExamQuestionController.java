package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.services.ExamQuestionService;

@Controller
@RequestMapping("/api/v2")
@CrossOrigin(origins = "*")
public class ExamQuestionController {
	private ExamQuestionService examQuestionService;

	@Autowired
	public ExamQuestionController(ExamQuestionService examQuestionService) {
		super();
		this.examQuestionService = examQuestionService;
	}
	
	
}
