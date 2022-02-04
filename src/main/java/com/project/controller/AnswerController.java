package com.project.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.project.entity.Answer;
import com.project.entity.Quiz;
import com.project.services.AnswerService;

@Controller
@RequestMapping(value = "api/v2")
@CrossOrigin(origins = "*")
public class AnswerController {

	@Autowired
	private AnswerService answerService;
	@GetMapping(value = "/answer")
	public ResponseEntity<List<Answer>> get(){
		List<Answer> list = answerService.get();
		return new ResponseEntity<List<Answer>>(list,HttpStatus.OK);
	}
	@GetMapping(value = "/answer/{id}")
	public ResponseEntity<Answer> getById(@PathVariable(name = "id") int answerId){
		try {
			Answer answer= answerService.getById(answerId);
			return new ResponseEntity<Answer>(answer,HttpStatus.OK);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity(null,HttpStatus.BAD_REQUEST);
	}
	@PostMapping(value = "/answer")
	public ResponseEntity<Answer> save(@RequestBody Answer answer){
		Answer newAnswer = answerService.save(answer);
		return new ResponseEntity<Answer>(newAnswer,HttpStatus.OK);
	}
	@PostMapping(value = "/answer/multiple")
	public ResponseEntity<List<Answer>> save(@RequestBody Answer[] answers){
		List<Answer> list = answerService.save(answers);
		return new ResponseEntity<List<Answer>>(list,HttpStatus.OK);
	}
	@PutMapping(value = "answer/update")
	public ResponseEntity<Answer> update(@RequestBody Answer updateAnswer){
		Answer answer= answerService.update(updateAnswer);
		return new ResponseEntity<Answer>(answer,HttpStatus.OK);
	}
	@PutMapping(value = "answer/update/multiple")
	public ResponseEntity<List<Answer>> update(@RequestBody Answer[] updateAnswer){
		List<Answer> answer= answerService.update(updateAnswer);
		return new ResponseEntity<List<Answer>>(answer,HttpStatus.OK);
	}
	@DeleteMapping(value = "answer/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Integer id){
		String response = answerService.delete(id);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	@PostMapping(value = "answer/delete/multiple")
	public ResponseEntity<String> deleteMultiple(@RequestBody List<Integer> ids){
		List<String> list = ids.stream().map(id -> this.answerService.delete(id)).collect(Collectors.toList());
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
}
