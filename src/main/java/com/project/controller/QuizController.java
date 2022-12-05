package com.project.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.ParametersAreNonnullByDefault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Exam;
import com.project.entity.Quiz;
import com.project.responseDTO.QuizCatDTO;
import com.project.responseDTO.QuizDetailDTO;
import com.project.responseDTO.QuizWithQuestionResponseDTO;
import com.project.entity.QuizCategory;
import com.project.entity.User;
import com.project.repositories.QuizCategoryRepository;
import com.project.repositories.QuizRepository;
import com.project.repositories.UserRepository;
import com.project.responseDTO.CategoryWithQuiz;
import com.project.services.QuizService;
import com.project.services.UserDetailsImpl;

@Controller
@RequestMapping(value = "/api/v2")
@CrossOrigin(origins = "*")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@Autowired
	private QuizCategoryRepository catRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private QuizRepository quizRepo;

	@PreAuthorize("hasRole('USER')")
	@PostMapping(value = "/quiz")
	public ResponseEntity<Quiz> save(@RequestBody Quiz quiz) {
		Authentication authenication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authenication.getPrincipal();
		User user = this.userRepo.findById(userDetails.getId()).get();
		quiz.setAuthor(user);
		Quiz newQuiz = this.quizService.save(quiz);
		return new ResponseEntity<Quiz>(newQuiz, HttpStatus.OK);
	}

	@PostMapping(value = "/quiz/multiple")
	public ResponseEntity<List<Quiz>> save(@RequestBody Quiz[] quizzes) {
		List<Quiz> list = quizService.save(quizzes);
		return new ResponseEntity<List<Quiz>>(list, HttpStatus.OK);
	}

	@GetMapping(value = "/quiz")
	public ResponseEntity<List<QuizCatDTO>> get() {
		List<QuizCatDTO> list = quizService.get().stream().map(quiz -> new QuizCatDTO(quiz))
				.collect(Collectors.toList());
		return new ResponseEntity<List<QuizCatDTO>>(list, HttpStatus.OK);
	}

	@GetMapping(value = "/quiz/category/{catId}")
	public ResponseEntity<List<Quiz>> get(@PathVariable(name = "catId") int categoryId) {
		List<Quiz> list = quizService.getQuizByCategory(categoryId);
		return new ResponseEntity<List<Quiz>>(list, HttpStatus.OK);
	}

	@GetMapping(value = "/quiz/categoryWithQuestions/{catId}")
	public ResponseEntity<List<QuizWithQuestionResponseDTO>> getQuizWithQuestions(
			@PathVariable(name = "catId") int categoryId) {
		List<QuizWithQuestionResponseDTO> list = quizRepo.getQuizWithQuestionByCatId(categoryId);
		return new ResponseEntity<List<QuizWithQuestionResponseDTO>>(list, HttpStatus.OK);
	}

	@GetMapping(value = "/quiz/getRelatedQuiz")
	public ResponseEntity<List<QuizCatDTO>> getRelatedQuiz(@RequestParam(name = "catId") int catId,
			@RequestParam(name = "quizId") int quizId) {
		List<QuizCatDTO> relatedQuiz = this.quizService.getRelatedQuiz(catId, quizId);
		return new ResponseEntity<List<QuizCatDTO>>(relatedQuiz, HttpStatus.OK);
	}

	@GetMapping(value = "/quiz/getQuizByCatAtHomepage")
	public ResponseEntity<Object> getQuizByCatAtHomepage() {
		Sort sortQuiz = Sort.by("quizName");
		Sort sortCat = Sort.by("categoryName");
		Pageable pageableQuiz = PageRequest.of(0, 8, sortQuiz);
		Pageable pageableCat = PageRequest.of(0, 6, sortCat);
		Page<QuizCategory> catPage = this.catRepo.findAll(pageableCat);
		List<CategoryWithQuiz> listResult = catPage.getContent().stream()
				.map(cate -> new CategoryWithQuiz(cate.getId(), cate.getCategoryName())).collect(Collectors.toList());
		for (CategoryWithQuiz e : listResult) {
			Page<Quiz> quizPage = this.quizRepo.getQuizByCatId(e.getCatId(), pageableQuiz);
			List<QuizCatDTO> quizContent = quizPage.getContent().stream().map(quiz -> new QuizCatDTO(quiz))
					.collect(Collectors.toList());
			e.setQuizzes(quizContent);
		}
		return ResponseEntity.ok(listResult);
	}

	@GetMapping(value = "/quiz/search/pagination")
	public ResponseEntity<Page<Quiz>> searchWithPagination(
			@RequestParam(name = "sortD", required = false, defaultValue = "2") Integer sortD,
			@RequestParam(name = "sortBy", required = false, defaultValue = "quizName") String sortBy,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "pageSize", required = false, defaultValue = "9") int pageSize,
			@RequestParam(name = "keyword", required = false) String keyword) {
		Pageable pageable;
		Sort sort;
		if (sortD == 1) {
			sort = Sort.by(sortBy).descending();
			pageable = PageRequest.of(page - 1, pageSize, sort);
		} else if (sortD == 2) {
			sort = Sort.by(sortBy).ascending();
			pageable = PageRequest.of(page - 1, pageSize, sort);
		}

		else {
			pageable = PageRequest.of(page - 1, pageSize);
		}
		Page<Quiz> quizList;
		if (keyword == null) {
			quizList = this.quizRepo.findAll(pageable);
		} else {
			quizList = this.quizRepo.searchQuizByName(keyword, pageable);
		}
		return new ResponseEntity<Page<Quiz>>(quizList, HttpStatus.OK);
	}

	@GetMapping(value = "/quiz/search")
	public ResponseEntity<List<Quiz>> search(@RequestParam(name = "keyword", required = false) String keyword) {
		List<Quiz> res = this.quizRepo.searchByName(keyword);
		return ResponseEntity.ok(res);
	}
//	@GetMapping(value = "/quiz/getQuizExam/{id}")
//	public ResponseEntity<List<Exam>> getQuizExam(@PathVariable(name = "id") int id){
//		List<Exam> list = this.quizRepo.getQuizExam(id);
//		return ResponseEntity.ok(list);
//	}

//	@GetMapping(value = "/quiz/{id}")
//	public ResponseEntity<Quiz> getById(@PathVariable(name = "id") int quizId) {
//		try {
//			Quiz quiz = quizService.getById(quizId);
//			return new ResponseEntity<Quiz>(quiz, HttpStatus.OK);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
//	}

	@GetMapping(value = "/quiz/{id}")
	public ResponseEntity<QuizDetailDTO> getById(@PathVariable(name = "id") int quizId) {
		try {
			Quiz quiz = quizService.getById(quizId);
			return new ResponseEntity<QuizDetailDTO>(new QuizDetailDTO(quiz), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value = "/quiz/update")
	public ResponseEntity<Quiz> update(@RequestBody Quiz updateQuiz) {
		Quiz quiz = quizService.update(updateQuiz);
		return new ResponseEntity<Quiz>(quiz, HttpStatus.OK);
	}

	@DeleteMapping(value = "quiz/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Integer id) {
		String response = quizService.delete(id);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
