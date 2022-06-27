package com.project.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.project.entity.Question;
import com.project.entity.QuizScore;
import com.project.entity.UserFavourites;
import com.project.repositories.AnswerRepository;
import com.project.repositories.QuestionRepository;
import com.project.repositories.QuizRepository;
import com.project.repositories.QuizScoreRepository;
import com.project.repositories.UserRepository;
import com.project.requestDTO.AnswerRequestDTO;
import com.project.responseDTO.AnswerResponseDTO;
import com.project.responseDTO.CorrectAnswerResponseDTO;
import com.project.services.AnswerService;
import com.project.services.QuestionService;
import com.project.services.UserDetailsImpl;

@Controller
@RequestMapping(value = "/api/v2")
@CrossOrigin(origins = "*")
public class QuestionController {
	@Autowired
	private QuizScoreRepository quizScoreRepo;
	
	@Autowired
	private AnswerRepository answerRepo;
	
	@Autowired
	private QuestionRepository questionRepo;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private QuizRepository quizRepo;
	
	@Autowired
	private UserRepository userRepo;
	@GetMapping(value = "/question")
	public ResponseEntity<List<Question>> get(){
		List<Question> list = questionService.get();
		return new ResponseEntity<List<Question>>(list,HttpStatus.OK);
	}
	@GetMapping(value = "/question/getCorrectAnswers/{quizId}")
	public ResponseEntity<List<Question>> getCorrectAnswers(@PathVariable(name = "quizId")
	int quizId){
		List<Question> list = questionRepo.getCorrectAnswers(quizId);
		return ResponseEntity.ok(list);
	}
	@GetMapping(value = "/question/testGetAnswers/{quizId}")
	public ResponseEntity<List<Question>> testGetAnswers(@PathVariable(name = "quizId")
	int quizId){
		List<Question> list = questionRepo.findQuestionByQuizId(quizId);
		return new ResponseEntity<List<Question>>(list,HttpStatus.OK);
	}
	@PreAuthorize(value = "hasRole('USER')")
	@PostMapping(value = "/question/postAnswers/{quizId}")
	public ResponseEntity<AnswerResponseDTO> postAnswer(
			@RequestBody AnswerRequestDTO[] answers,
			@PathVariable(name = "quizId") int quizId){
		int totalScore = 0;
		List<Question> correctAnswers = this.questionRepo.getCorrectAnswers(quizId);
		List<List<Integer>> correctAnswerIds = this.questionService.getAnswerIds(correctAnswers);
		List<CorrectAnswerResponseDTO> correctAnswerResponse = new ArrayList<CorrectAnswerResponseDTO>();
		// so sanh
//		Stream<AnswerRequestDTO> answerStream = Stream.of(answers);
		List<AnswerRequestDTO> sortedAnswerRequestDTO = Arrays.asList(answers).stream().sorted(Comparator.comparingInt(AnswerRequestDTO::getQuestionId))
				.collect(Collectors.toList());
		for(int i = 0; i < sortedAnswerRequestDTO.size(); i++) {
			if(this.answerService.equalsIngnoreOrder(sortedAnswerRequestDTO.get(i).getAnswerIds(),correctAnswerIds.get(i))) {
				CorrectAnswerResponseDTO res = new CorrectAnswerResponseDTO
						(true,correctAnswerIds.get(i),sortedAnswerRequestDTO.get(i).getQuestionId());
				correctAnswerResponse.add(res);
				totalScore++;
			}
			else {
				CorrectAnswerResponseDTO res = new CorrectAnswerResponseDTO
						(false,correctAnswerIds.get(i),sortedAnswerRequestDTO.get(i).getQuestionId());
				correctAnswerResponse.add(res);
			}
		}
		// get user
		Authentication authenication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authenication.getPrincipal();
		String score = totalScore + "/" + correctAnswers.size();
		//save score
		QuizScore quizScore = new QuizScore(this.quizRepo.findById(quizId).get(),
				userRepo.findById(userDetails.getId()).get(),Date.valueOf(LocalDate.now()),
						score);
		this.quizScoreRepo.save(quizScore);
		return ResponseEntity.ok(new AnswerResponseDTO(correctAnswerResponse,totalScore));
	}
	
	@PostMapping(value = "/question/postAnswersTest/{quizId}")
	public ResponseEntity<AnswerResponseDTO> postAnswerTest(
			@RequestBody AnswerRequestDTO[] answers,
			@PathVariable(name = "quizId") int quizId){
		int totalScore = 0;
		List<Question> correctAnswers = this.questionRepo.getCorrectAnswers(quizId);
		List<List<Integer>> correctAnswerIds = this.questionService.getAnswerIds(correctAnswers);
		List<CorrectAnswerResponseDTO> correctAnswerResponse = new ArrayList<CorrectAnswerResponseDTO>();
		// so sanh
//		Stream<AnswerRequestDTO> answerStream = Stream.of(answers);
		List<AnswerRequestDTO> sortedAnswerRequestDTO = Arrays.asList(answers).stream().sorted(Comparator.comparingInt(AnswerRequestDTO::getQuestionId))
				.collect(Collectors.toList());
		for(int i = 0; i < sortedAnswerRequestDTO.size(); i++) {
			if(this.answerService.equalsIngnoreOrder(sortedAnswerRequestDTO.get(i).getAnswerIds(),correctAnswerIds.get(i))) {
				CorrectAnswerResponseDTO res = new CorrectAnswerResponseDTO
						(true,correctAnswerIds.get(i),sortedAnswerRequestDTO.get(i).getQuestionId());
				correctAnswerResponse.add(res);
				totalScore++;
			}
			else {
				CorrectAnswerResponseDTO res = new CorrectAnswerResponseDTO
						(false,correctAnswerIds.get(i),sortedAnswerRequestDTO.get(i).getQuestionId());
				correctAnswerResponse.add(res);
			}
		}
	
		return ResponseEntity.ok(new AnswerResponseDTO(correctAnswerResponse,totalScore));
	}
	
	@PostMapping(value = "/question/postAnswer/{examId}")
	public ResponseEntity<AnswerResponseDTO> postAnswers(
			@RequestBody AnswerRequestDTO[] answers,
			@PathVariable(name = "examId") int examId){
		int totalScore = 0;
		List<Question> correctAnswers = this.questionRepo.getCorrectAnswersFromExam(examId);
		for(Question q:correctAnswers) {
			System.out.println(q.getQuestionId());
			for(Answer a:q.getAnswers()) {
				System.out.println(a.getAnswerId());
			}
		}
		List<List<Integer>> correctAnswerIds = this.questionService.getAnswerIds(correctAnswers);
		List<CorrectAnswerResponseDTO> correctAnswerResponse = new ArrayList<CorrectAnswerResponseDTO>();
		// so sanh
//		Stream<AnswerRequestDTO> answerStream = Stream.of(answers);
		List<AnswerRequestDTO> sortedAnswerRequestDTO = Arrays.asList(answers).stream().sorted(Comparator.comparingInt(AnswerRequestDTO::getQuestionId))
				.collect(Collectors.toList());
		for(int i = 0; i < sortedAnswerRequestDTO.size(); i++) {
			if(this.answerService.equalsIngnoreOrder(sortedAnswerRequestDTO.get(i).getAnswerIds(),correctAnswerIds.get(i))) {
				CorrectAnswerResponseDTO res = new CorrectAnswerResponseDTO
						(true,correctAnswerIds.get(i),sortedAnswerRequestDTO.get(i).getQuestionId());
				correctAnswerResponse.add(res);
				totalScore++;
			}
			else {
				CorrectAnswerResponseDTO res = new CorrectAnswerResponseDTO
						(false,correctAnswerIds.get(i),sortedAnswerRequestDTO.get(i).getQuestionId());
				correctAnswerResponse.add(res);
			}
		}
	
		return ResponseEntity.ok(new AnswerResponseDTO(correctAnswerResponse,totalScore));
	}
	
	@GetMapping(value = "/question/quizId/{quizId}")
	public ResponseEntity<List<Question>> get(@PathVariable(name = "quizId") Integer quizId){
		List<Question> list = questionService.getByQuizId(quizId);
		return new ResponseEntity<List<Question>>(list,HttpStatus.OK);
	}
	@GetMapping(value = "/question/{id}")
	public ResponseEntity<Question> getById(@PathVariable(name = "id") int questionId){
		try {
			Question question= questionService.getById(questionId);
			return new ResponseEntity<Question>(question,HttpStatus.OK);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity(null,HttpStatus.BAD_REQUEST);
	}
	@PostMapping(value = "/question")
	public ResponseEntity<Question> save(@RequestBody Question question){
		Question newQuestion = questionService.save(question);
		return new ResponseEntity<Question>(newQuestion,HttpStatus.OK);
	}
	@PostMapping(value = "/question/multiple")
	public ResponseEntity<List<Question>> save(@RequestBody Question[] questions){
		List<Question> list = questionService.save(questions);
		return new ResponseEntity<List<Question>>(list,HttpStatus.OK);
	}
	@PutMapping(value = "/question/update")
	public ResponseEntity<Question> update(@RequestBody Question updateQuestion){
		Question question= questionService.update(updateQuestion);
		return new ResponseEntity<Question>(question,HttpStatus.OK);
	}
	@PutMapping(value = "question/update/multiple")
	public ResponseEntity<List<Question>> update(@RequestBody Question[] updateQuestion){
		List<Question> question= questionService.update(updateQuestion);
		return new ResponseEntity<List<Question>>(question,HttpStatus.OK);
	}
	@DeleteMapping(value = "question/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Integer id){
		String response = questionService.delete(id);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	@PostMapping(value = "question/delete/multiple")
	public ResponseEntity<String> deleteMultiple(@RequestBody List<Integer> ids){
		ids.stream().map(id -> this.questionService.delete(id));
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
}
