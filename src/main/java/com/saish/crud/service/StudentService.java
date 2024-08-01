package com.saish.crud.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.saish.crud.dto.MyUser;
import com.saish.crud.dto.Student;
import com.saish.crud.helper.AES;
import com.saish.crud.helper.CloudinaryImageHelper;
import com.saish.crud.helper.EmailSendingHelper;
import com.saish.crud.repository.MyUserRepository;
import com.saish.crud.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class StudentService {

	@Autowired
	MyUser myUser;

	@Autowired
	MyUserRepository userRerpository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	EmailSendingHelper mailHelper;

	@Autowired
	CloudinaryImageHelper imageHelper;

	public String signup(ModelMap map) {
		map.put("signup", "signup");
		map.put("myUser", myUser);
		return "main.html";
	}

	public String signup(MyUser myUser, BindingResult result, ModelMap map) {
		if (userRerpository.existsByEmail(myUser.getEmail()))
			result.rejectValue("email", "error.email", "* Email Should be Unique");

		if (result.hasErrors()) {
			map.put("signup", "signup");
			return "main.html";
		}

		else {
			int otp = new Random().nextInt(100000, 1000000);
			myUser.setOtp(otp);
			myUser.setPassword(AES.encrypt(myUser.getPassword(), "123"));
			mailHelper.sendEmail(myUser);
			userRerpository.save(myUser);
			map.put("success", "Otp Sent Success, Check your Email");
			map.put("id", myUser.getId());
			map.put("otp", "otp");
			return "main.html";
		}
	}

	public String verifyOtp(int id, int otp, ModelMap map) {
		MyUser myUser = userRerpository.findById(id).orElseThrow();
		if (myUser.getOtp() == otp) {
			myUser.setVerified(true);
			userRerpository.save(myUser);
			map.put("success", "Account Created successfully");
			return "main.html";
		} else {
			map.put("failure", "Invalid OTP, Try Again");
			map.put("id", myUser.getId());
			map.put("otp", "otp");
			return "main.html";
		}
	}

	public String login(ModelMap map) {
		map.put("login", "login");
		return "main.html";
	}

	public String fetchRecords(ModelMap map) {
		List<Student> list = studentRepository.findAll();
		if (list.isEmpty()) {
			map.put("failure", "No Data Found");
			return "main.html";
		} else {
			map.put("fetch", "fetch");
			map.put("list", list);
			return "main.html";
		}
	}

	public String login(HttpSession session, String email, String password, ModelMap map) {
		MyUser myUser = userRerpository.findByEmail(email);
		if (myUser == null) {
			map.put("failure", "Invalid email address");
			map.put("login", "login");
			return "home.html";
		} else {
			if (password.equals(AES.decrypt(myUser.getPassword(), "123"))) {
				if (myUser.isVerified()) {
					session.setAttribute("user", myUser);
					map.put("success", "Login Success");
					return fetchRecords(map);
				} else {
					int otp = new Random().nextInt(100000, 1000000);
					myUser.setOtp(otp);
					mailHelper.sendEmail(myUser);
					userRerpository.save(myUser);
					map.put("success", "Otp Sent Success, Check your Email");
					map.put("id", myUser.getId());
					map.put("otp", "otp");
					return "main.html";
				}
			} else {
				map.put("failure", "Invalid Password");
				map.put("login", "login");
				return "main.html";
			}

		}
	}

	public String logout(ModelMap map, HttpSession session) {
		session.removeAttribute("user");
		map.put("success", "Logout Success");
		return "main.html";
	}

	public String insert(HttpSession session, ModelMap map) {
		if (session.getAttribute("user") != null) {
			map.put("insert", "insert");
			return fetchRecords(map);
		} else {
			map.put("failure", "Invalid session");
			return "main.html";
		}
	}

	public String insert(Student student, MultipartFile image, HttpSession session, ModelMap map) {
		if (session.getAttribute("user") != null) {
			student.setPicture(imageHelper.addToCloudinary(image));
			studentRepository.save(student);
			map.put("success", "Record Saved Successfully");
			return fetchRecords(map);
		} else {
			map.put("failure", "Invalid session");
			return "main.html";
		}
	}

	public String delete(int id, HttpSession session, ModelMap map) {
		if (session.getAttribute("user") != null) {
			studentRepository.deleteById(id);
			map.put("success", "Record Delete Success");
			return fetchRecords(map);
		} else {
			map.put("failure", "Invalid session");
			return "main.html";
		}
	}

	public String edit(int id, HttpSession session, ModelMap map) {
		if (session.getAttribute("user") != null) {
			Student student = studentRepository.findById(id).orElseThrow();
			map.put("student", student);
			map.put("update", "update");
			return fetchRecords(map);
		} else {
			map.put("failure", "Invalid session");
			return "main.html";
		}
	}

	public String update(Student student, HttpSession session, ModelMap map, MultipartFile image) {
		if (session.getAttribute("user") != null) {
			student.setPicture(imageHelper.addToCloudinary(image));
			studentRepository.save(student);
			map.put("success", "Record Updated Successfully");
			return fetchRecords(map);
		} else {
			map.put("failure", "Invalid session");
			return "main.html";
		}
	}

}
