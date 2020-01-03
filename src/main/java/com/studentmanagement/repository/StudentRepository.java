package com.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentmanagement.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
