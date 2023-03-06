package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentController {
	@Autowired
	private StudentRepository studentRepository;

	@GetMapping("/welcome")
	public String welcome() {
		return "Hello Gb";
	}

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Student> listAll() {
		List<Student> listStudents = studentRepository.findAll();
		return listStudents;
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Optional<Student> getNameById(@PathVariable int id) {
		Optional<Student> students = studentRepository.findById(id);
		return students;
	}

}
