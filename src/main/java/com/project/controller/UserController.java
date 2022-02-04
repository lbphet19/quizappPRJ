package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.project.entity.Quiz;
import com.project.entity.User;
import com.project.repositories.QuizRepository;
import com.project.repositories.UserRepository;
import com.project.services.QuizService;
import com.project.services.UserDetailsImpl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v2")
public class UserController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private QuizRepository quizRepo;
	
//	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/user")
	public ResponseEntity<List<User>> getAll(){
		List<User> list = this.userRepo.findAll();
		return ResponseEntity.ok(list);
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUser(@PathVariable(name = "userId") int userId){
		User user = this.userRepo.findById(userId).get();
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/user/getUserQuiz")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Quiz>> getUserQuiz(){
		Authentication authenication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authenication.getPrincipal();
		User user = this.userRepo.findById(userDetails.getId()).get();
		return ResponseEntity.ok(quizService.getUserQuiz(user));
	}
	
	@PostMapping("/user/addFavourite/{quizId}")
	public ResponseEntity<String> changeFavourite(@PathVariable(name = "quizId") int quizId){
		Authentication authenication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authenication.getPrincipal();
		User user = this.userRepo.findById(userDetails.getId()).get();
		Quiz quiz = quizRepo.findById(quizId).get();
		if(user.getFavouriteQuizList().contains(quiz)) {
			user.removeQuizFromFavouriteList(quiz);
		}
		else {
			user.addQuizToFavouriteList(quiz);
		}
		this.userRepo.save(user);
		return ResponseEntity.ok("Success");
	}
	@PutMapping("/user/update")
	public ResponseEntity<User> updateUser(@RequestBody User user){
		User userUpdate = this.userRepo.findById(user.getId()).get();
		userUpdate.setName(user.getName());
		this.userRepo.save(user);
		return ResponseEntity.ok(user);
//		userUpdate.set
	}
	@PutMapping("/user/changePassword")
	public ResponseEntity<User> changeUserPassword(@RequestBody User user){
		User userUpdate = this.userRepo.findById(user.getId()).get();
		userUpdate.setPassword(user.getPassword());
		this.userRepo.save(user);
		return ResponseEntity.ok(user);
//		userUpdate.set
	}
}
