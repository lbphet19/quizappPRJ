package com.project.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.services.UserDetailsImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	private UserDetailsService userService;
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/testDate")
	public ResponseEntity<Object> getDate() {
		return ResponseEntity.ok((LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));
	}
	
	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> testUserIdAccess(@PathVariable(name = "id") String userId) throws Exception {
		Authentication authenication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authenication.getPrincipal();
		if(userDetails.getId() != Long.valueOf(userId)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("You don't have access to this source");
		}
		return ResponseEntity.ok("User Content " + userId);
	}
	
//	@GetMapping("/user/getMyQuiz")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	public ResponseEntity<?> getUserQuiz(){
//		Authentication authenication = SecurityContextHolder.getContext().getAuthentication();
//		UserDetailsImpl userDetails = (UserDetailsImpl) authenication.getPrincipal();
//		
//		return ResponseEntity.ok("User Content " + userId);
//	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
