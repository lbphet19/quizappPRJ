package com.project.controller;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.project.entity.TokenDTO;
import com.project.jwt.JwtUtils;
import com.project.payload.response.JwtResponse;
import com.project.repositories.RoleRepository;
import com.project.repositories.UserRepository;
import com.project.services.SocialUserService;
import com.project.services.UserDetailsImpl;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/social")

public class SocialController {

	@Value("${google.clientId}")
	String googleClientId;

	@Value("${secretPsw}")
	String secretPsw;

	@Autowired
	SocialUserService socialUserService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;
	@GetMapping("/test")
	public ResponseEntity<String> get(){
		return ResponseEntity.ok("Hello!");
	}
	@PostMapping("/google")
	public ResponseEntity<?> google(@RequestBody TokenDTO TokenDTO) throws IOException {
		final NetHttpTransport transport = new NetHttpTransport();
		final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
		GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
				.setAudience(Collections.singletonList(googleClientId));
		final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), TokenDTO.getValue());
		final GoogleIdToken.Payload payload = googleIdToken.getPayload();
//        neu co thoi gian thi chinh class User
		JwtResponse res = new JwtResponse();
//		System.out.println((String)payload.get("name"));
//		.getEmail());
		if (userRepository.existsByEmail(payload.getEmail())) {
			System.out.println("exist");
//        	login with already stored user
			User user = userRepository.findByEmail(payload.getEmail()).get();
			res = socialUserService.login(userRepository.findByEmail(payload.getEmail()).get());
		} else {
			System.out.println("new");
//        	if user not created => create then signin
			User createUser = socialUserService.createUser(payload.getEmail(),(String)payload.get("name"));
			res = socialUserService.login(createUser);
		}
		
		return ResponseEntity.ok(res);
	}

	@PostMapping("/facebook")
	public ResponseEntity<?> facebook(@RequestBody TokenDTO TokenDTO) throws IOException {
		Facebook facebook = new FacebookTemplate(TokenDTO.getValue());
		final String[] fields = { "email", "picture" };
		org.springframework.social.facebook.api.User user = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
		Object o = new Object();
		return ResponseEntity.ok(o);
	}

}
