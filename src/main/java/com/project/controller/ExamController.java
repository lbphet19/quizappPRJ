package com.project.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.project.entity.Exam;
import com.project.entity.ExamQuestion;
import com.project.entity.Question;
import com.project.entity.Quiz;
import com.project.entity.QuizCategory;
import com.project.entity.User;
import com.project.entity.UserExamScore;
import com.project.repositories.ExamQuestionRepository;
import com.project.repositories.ExamRepository;
import com.project.repositories.QuestionRepository;
import com.project.repositories.QuizCategoryRepository;
import com.project.repositories.UserExamScoreRepository;
import com.project.repositories.UserRepository;
import com.project.requestDTO.ExamQuestionRequestDTO;
import com.project.responseDTO.CategoryWithExam;
import com.project.responseDTO.CategoryWithQuiz;
import com.project.responseDTO.ExamWithTimeAndQuestionsDTO;
import com.project.responseDTO.QuizCatDTO;
import com.project.responseDTO.QuizWithQuestionResponseDTO;
import com.project.responseDTO.QuizWithQuestionsDTO;
import com.project.services.ExamQuestionService;
import com.project.services.ExamService;
import com.project.services.UserDetailsImpl;

@Controller
@RequestMapping(value = "/api/v2")
@CrossOrigin(origins = "*")
public class ExamController {
	private ExamService examService;
	private QuestionRepository questionRepo;
	private ExamQuestionService examQuestionService;
	private ExamQuestionRepository examQuestionRepo;
	private ExamRepository examRepo;
	private UserExamScoreRepository userExamRepo;
	private UserRepository userRepo;
	private QuizCategoryRepository catRepo;
	
	@Autowired
	public ExamController(ExamService examService, QuestionRepository q, 
			ExamQuestionService examQuestionService, ExamQuestionRepository examQuestionRepo,
			ExamRepository examRepo, UserExamScoreRepository userExamRepo,
			UserRepository userRepo, QuizCategoryRepository catRepo) {
		super();
		this.examService = examService;
		this.questionRepo = q;
		this.examQuestionService = examQuestionService;
		this.examQuestionRepo = examQuestionRepo;
		this.examRepo = examRepo;
		this.userExamRepo = userExamRepo;
		this.userRepo = userRepo;
		this.catRepo = catRepo;
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
	@GetMapping("/exam/category/{categoryId}")
	public ResponseEntity<List<Exam>> getByCategoryId(@PathVariable(name = "categoryId") int id){
		List<Exam> list = this.examRepo.getExamByCategoryId(id);
		return ResponseEntity.ok(list);
//		abc
	}
	@GetMapping("/exam/{id}/getQuestionsIds")
	public ResponseEntity<List<Integer>> getExamQuestionIds(@PathVariable(name = "id") int id){
		List<Integer> res = this.examQuestionRepo.getExamQuestionIds(id);
		return ResponseEntity.ok(res); 
	}
	@GetMapping("/exam/{id}/getCategoryId")
	public ResponseEntity<Integer> getExamCategoryId(@PathVariable(name = "id") int id){
		int res = this.examRepo.getExamCategoryId(id);
		return ResponseEntity.ok(res); 
	}
//	{id}: lay quiz + questions
	@GetMapping("/exam/{id}/getQuestions")
	public ResponseEntity<Set<QuizWithQuestionsDTO>> getQuestionsByExamId(@PathVariable(name = "id") int id){
		Exam ex = this.examRepo.findById(id).get();
		Set<QuizWithQuestionsDTO> res = this.examRepo.getQuestionsByCatId(ex.getQuizCategory().getId());
		return ResponseEntity.ok(res); 
	}
	
	@GetMapping("/exam/{id}/getTest")
	public ResponseEntity<List<Quiz>> getTest(@PathVariable(name = "id") int id){
		Exam ex = this.examRepo.findById(id).get();
		System.out.println(ex.getQuizCategory().getId());
		List<Quiz> res = this.examRepo.getTest(ex.getQuizCategory().getId());
		return ResponseEntity.ok(res); 
	}
	
	@GetMapping(value = "/exam/home")
	public ResponseEntity<Object> getExamByCatAtHomepage() {
		Sort sortQuiz = Sort.by("examName");
		Sort sortCat = Sort.by("categoryName");
		Pageable pageableQuiz = PageRequest.of(0, 8, sortQuiz);
		Pageable pageableCat = PageRequest.of(0, 6, sortCat);
		Page<QuizCategory> catPage = this.catRepo.findAll(pageableCat);
		List<CategoryWithExam> listResult = catPage.getContent().stream()
				.map(cate -> new CategoryWithExam(cate.getId(), cate.getCategoryName())).collect(Collectors.toList());
		for (CategoryWithExam e : listResult) {
			System.out.println(e.getCatId());
			Page<Exam> examPage = this.examRepo.getExamByCategoryIdPagination(e.getCatId(), pageableQuiz);
			List<Exam> examContent = examPage.getContent();
			e.setExams(examContent);
		}
		return ResponseEntity.ok(listResult);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/exam/{id}/getQuestion")
	public ResponseEntity<ExamWithTimeAndQuestionsDTO> getQuestionByExamId(@PathVariable(name = "id") int id){
//		lay thong tin user -> set thoi gian vao lam bai
		Authentication authenication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authenication.getPrincipal();
		List<UserExamScore> userExamScore = userExamRepo.findUserLastAttempt(id, userDetailsImpl.getId());
		
		List<Question> questions = this.examQuestionRepo.getQuestionByExamId(id);
		if(userExamScore.size() > 0) {
//			dang lam bai do
			return ResponseEntity.ok(new ExamWithTimeAndQuestionsDTO(userExamScore.get(0).getEndTime() 
			- LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), questions));
		}
		else {
			Exam ex = examRepo.findById(id).get();
			User user = userRepo.findById(userDetailsImpl.getId()).get();
			long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
			UserExamScore insert = new UserExamScore(ex,user,now,now + ex.getTime(),null,"proccessing");
			this.userExamRepo.save(insert);
			return ResponseEntity.ok(new ExamWithTimeAndQuestionsDTO(ex.getTime(), questions));
		}
		
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping("/exam/{id}/getQuestionForResult")
	public ResponseEntity<List<Question>> getQuestionForResult(@PathVariable(name = "id") int id){
		List<Question> questions = this.examQuestionRepo.getQuestionByExamId(id);
		return ResponseEntity.ok(questions);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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
		 List<Integer> inserts = new LinkedList<>();
		 List<Integer> deletes = new LinkedList<>();
		 List<Integer> oldIds = this.examQuestionRepo.getExamQuestionIds(dto.getExamId());
		 List<Integer> newIds = dto.getQuestionId();
		 for(int i: newIds) {
			 if(oldIds.size() == 0 || !oldIds.contains(i)) {
//				 them vao
				 inserts.add(i);
			 }
		 }
		 for(int i: oldIds) {
			 if(newIds.size() == 0 || !newIds.contains(i)) {
//				 them vao
				 deletes.add(i);
			 }
		 }
		 List<Question> ques = this.questionRepo.findAllById(inserts);
		 Exam ex = this.examService.findById(dto.getExamId());
		 List<ExamQuestion> listExQues = ques.stream().map(q -> {
			 return new ExamQuestion(q, ex);
		 }).collect(Collectors.toList());
		 if(deletes.size() > 0) {
			 this.examQuestionRepo.deleteExQuesById(dto.getExamId(), deletes);
		 }
		 return ResponseEntity.ok(this.examQuestionService.saveAll(listExQues));
	}
}
