package com.project.services;

import java.util.List;

import com.project.entity.Exam;

public interface BaseService<T,ID> {
	List<T> findAll();
	T findById(ID id);
	String delete(ID id);
	T save(T e);
	T update(T update);
}
