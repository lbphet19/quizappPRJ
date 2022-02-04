package com.project.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.entity.ERole;
import com.project.entity.Role;
import com.project.entity.User;
import com.project.jwt.JwtUtils;
import com.project.payload.response.JwtResponse;
import com.project.repositories.RoleRepository;
import com.project.repositories.UserRepository;


@Service
public class SocialUserService {
	@Value("${secretPsw}")
	String secretPsw;

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
	
	public JwtResponse login(User user) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), secretPsw));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()
				,userDetails.getName(), roles);
	
//		List<String> roles = Arrays.asList("role1","role2");
//		return new JwtResponse("abc", 1L, "username", "email", roles);
	
	}
	
	public User createUser(String email,String name) {
		User user = new User(email, email,
				passwordEncoder.encode(secretPsw),name);
//        add roles
		Set<Role> roles = new HashSet<Role>();
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
		return user;
	}
}
