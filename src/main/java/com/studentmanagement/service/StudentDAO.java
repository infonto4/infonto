package com.studentmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagement.model.Student;
import com.studentmanagement.repository.StudentRepository;

@Service
public class StudentDAO {
	@Autowired
	StudentRepository studentRepository;
	
	/*to save an employee*/
	
	public Student save(Student std) {
		return studentRepository.save(std);
	}
	
	
	/* search all employees*/
	
	public List<Student> findAll(){
		return studentRepository.findAll();
	}
	
	
	/*get an employee by id*/
	public Student findOne(Integer id) {
		return studentRepository.getOne(id);
	}
	
	
	/*delete an employee*/
	
	public void delete(Student std) {
		studentRepository.delete(std);
	}
	

}
