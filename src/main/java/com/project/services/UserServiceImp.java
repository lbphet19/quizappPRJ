package com.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.User;
import com.project.repositories.UserRepository;

@Service
public class UserServiceImp {
	
	@Autowired
	private UserRepository userRepo;
	
	public User getUser(int id) {
		return this.userRepo.findById(id).get();
	}
	
}
