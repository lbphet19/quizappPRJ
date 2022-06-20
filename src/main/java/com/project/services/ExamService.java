package com.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Exam;
import com.project.repositories.ExamRepository;


public interface ExamService extends BaseService<Exam, Integer>{
	
}
