package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.QuizCategory;
import com.project.services.QuizCategoryService;

@Controller
@RequestMapping("api/v2")
@CrossOrigin(origins = "*")
public class QuizCategoryController {
	@Autowired
	private QuizCategoryService quizCatService;
	
	@RequestMapping("/category")
	public ResponseEntity<List<QuizCategory>> get(){
		return ResponseEntity.ok(this.quizCatService.findAll());
	}
}
