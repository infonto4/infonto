package com.searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.searchengine.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
