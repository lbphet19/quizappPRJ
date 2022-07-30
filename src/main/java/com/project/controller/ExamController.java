package com.project.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.security.access.prepost.PreAuthorize;
import com.project.entity.Exam;
import com.project.entity.ExamQuestion;
import com.project.entity.Question;
import com.project.repositories.ExamQuestionRepository;
import com.project.repositories.ExamRepository;
import com.project.repositories.QuestionRepository;
import com.project.requestDTO.ExamQuestionRequestDTO;
import com.project.services.ExamQuestionService;
import com.project.services.ExamService;

@Controller
@RequestMapping(value = "/api/v2")
@CrossOrigin(origins = "*")
public class ExamController {
	private ExamService examService;
	private QuestionRepository questionRepo;
	private ExamQuestionService examQuestionService;
	private ExamQuestionRepository examQuestionRepo;
	
	@Autowired
	public ExamController(ExamService examService, QuestionRepository q, 
			ExamQuestionService examQuestionService, ExamQuestionRepository examQuestionRepo) {
		super();
		this.examService = examService;
		this.questionRepo = q;
		this.examQuestionService = examQuestionService;
		this.examQuestionRepo = examQuestionRepo;
	}
	
	@GetMapping("/exam")
	public ResponseEntity<List<Exam>> findAll(){
		return ResponseEntity.ok(this.examService.findAll());
	}
	
	@GetMapping("/exam/{id}")
	public ResponseEntity<Exam> getById(@PathVariable(name = "id") int id){
		Exam ex = this.examService.findById(id);
		return ResponseEntity.ok(ex);
//		abc
	}
	
	@GetMapping("/exam/{id}/getQuestion")
	public ResponseEntity<List<Question>> getQuestionByExamId(@PathVariable(name = "id") int id){
		List<Question> questions = this.examQuestionRepo.getQuestionByExamId(id);
		return ResponseEntity.ok(questions); 
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/exam")
	public ResponseEntity<Exam> save(@RequestBody Exam ex){
		Exam insertEx = this.examService.save(ex);
		return ResponseEntity.ok(insertEx);
	}
	
	@PutMapping("/exam")
	public ResponseEntity<Exam> update(@RequestBody Exam ex){
		Exam updateEx = this.examService.update(ex);
		return ResponseEntity.ok(updateEx);
	}
	
	@DeleteMapping("/exam/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") int id){
		return ResponseEntity.ok(this.examService.delete(id));
	}
	
	@PostMapping("/exam/addQuestion")
	public ResponseEntity<List<ExamQuestion>> postQuestion(@RequestBody ExamQuestionRequestDTO dto){
		 List<Question> ques = this.questionRepo.findAllById(dto.getQuestionId());
		 Exam ex = this.examService.findById(dto.getExamId());
		 List<ExamQuestion> listExQues = ques.stream().map(q -> {
			 return new ExamQuestion(q, ex);
		 }).collect(Collectors.toList());
		 return ResponseEntity.ok(this.examQuestionService.saveAll(listExQues));
	}
}
