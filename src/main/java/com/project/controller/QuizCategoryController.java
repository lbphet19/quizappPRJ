package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.QuizCategory;
import com.project.repositories.QuizCategoryRepository;
import com.project.services.QuizCategoryService;

@Controller
@RequestMapping("api/v2")
@CrossOrigin(origins = "*")
public class QuizCategoryController {
	@Autowired
	private QuizCategoryService quizCatService;
	private QuizCategoryRepository quizCatRepo;
	
	
	@Autowired
	public QuizCategoryController(QuizCategoryService quizCatService, QuizCategoryRepository quizCatRepo) {
		super();
		this.quizCatService = quizCatService;
		this.quizCatRepo = quizCatRepo;
	}
	@GetMapping("/category")
	public ResponseEntity<List<QuizCategory>> get(){
		return ResponseEntity.ok(this.quizCatService.findAll());
	}
	@GetMapping("/category/{id}")
	public ResponseEntity<QuizCategory> getById(@PathVariable(name = "id") int catId){
		return ResponseEntity.ok(this.quizCatRepo.findById(catId).get());
	}
//	@GetMapping("/category/{id}/withExam")
//	public ResponseEntity<QuizCategory> getByIdWithExam(@PathVariable(name = "id") int catId){
//		return ResponseEntity.ok(this.quizCatRepo.getByIdWithExam(catId));
//	}
	@GetMapping("/category/childCategory")
	public ResponseEntity<List<QuizCategory>> getChildCat(){
		return ResponseEntity.ok(this.quizCatRepo.getChildQuizCategory());
	}
	@GetMapping("/category/rootCategory")
	public ResponseEntity<List<QuizCategory>> getRootCat(){
		return ResponseEntity.ok(this.quizCatRepo.getRootQuizCategory());
	}
	@PostMapping("/category")
	public ResponseEntity<QuizCategory> post(@RequestBody QuizCategory cat){
		return ResponseEntity.ok(this.quizCatService.save(cat));
	}
	@PutMapping("/category")
	public ResponseEntity<QuizCategory> put(@RequestBody QuizCategory cat){
		return ResponseEntity.ok(this.quizCatService.update(cat));
	}
}
