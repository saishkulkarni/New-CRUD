package com.saish.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.saish.crud.dto.MyUser;
import com.saish.crud.dto.Student;
import com.saish.crud.service.StudentService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MyController {

	@Autowired
	StudentService studentService;

	@GetMapping("/")
	public String loadFirstPage() {
		return "main.html";
	}

	@GetMapping("/signup")
	public String loadSignup(ModelMap map) {
		return studentService.signup(map);
	}

	@PostMapping("/signup")
	public String signup(@Valid MyUser myUser, BindingResult result, ModelMap map) {
		return studentService.signup(myUser, result, map);
	}

	@PostMapping("/verify-otp")
	public String verify(@RequestParam int id, @RequestParam int otp, ModelMap map) {
		return studentService.verifyOtp(id, otp, map);
	}

	@GetMapping("/login")
	public String loadLogin(ModelMap map) {
		return studentService.login(map);
	}

	@PostMapping("/login")
	public String login(HttpSession session, @RequestParam String email, @RequestParam String password, ModelMap map) {
		return studentService.login(session, email, password, map);
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, ModelMap map) {
		return studentService.logout(map, session);
	}

	@GetMapping("/insert")
	public String insert(HttpSession session, ModelMap map) {
		return studentService.insert(session, map);
	}

	@PostMapping("/insert")
	public String insert(Student student, HttpSession session, ModelMap map, @RequestParam MultipartFile image) {
		return studentService.insert(student, image, session, map);
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, HttpSession session, ModelMap map) {
		return studentService.delete(id,session,map);
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, HttpSession session, ModelMap map) {
		return studentService.edit(id,session,map);
	}

	@PostMapping("/update")
	public String update(Student student, HttpSession session, ModelMap map, @RequestParam MultipartFile image) {
		return studentService.update(student,session,map,image);
	}
}
