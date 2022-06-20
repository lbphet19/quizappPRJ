package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

}
