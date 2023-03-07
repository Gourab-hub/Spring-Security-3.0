package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AuthRequest;
import com.example.entity.Student;
import com.example.entity.UserEntity;
import com.example.repository.StudentRepository;
import com.example.repository.UserInfoRepository;
import com.example.services.JwtService;

@RestController
@RequestMapping("/students")
public class StudentController {
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	

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
	
	@PostMapping("/new")
	public UserEntity saveUser(@RequestBody UserEntity userEntity) {
		userEntity.setPassword(encoder.encode(userEntity.getPassword()));
		userInfoRepository.save(userEntity);
		return userEntity;
	}
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		if (authenticate.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUserName());
		}else {
			throw new UsernameNotFoundException("invalid user request");
		}
	
		
	}

}
