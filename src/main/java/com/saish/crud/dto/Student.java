package com.saish.crud.dto;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Student {
	@Id
	@GeneratedValue(generator = "x")
	@SequenceGenerator(initialValue = 1001, allocationSize = 1, name = "x")
	private int id;
	private String name;
	private String standard;
	private LocalDate dob;
	private long mobile;
	private int subject1;
	private int subject2;
	private int subject3;
	private int subject4;
	private int subject5;
	private int subject6;
	private String picture;

	public double getPercentage() {
		return (subject1 + subject2 + subject3 + subject4 + subject5 + subject6) / 6.0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public int getSubject1() {
		return subject1;
	}

	public void setSubject1(int subject1) {
		this.subject1 = subject1;
	}

	public int getSubject2() {
		return subject2;
	}

	public void setSubject2(int subject2) {
		this.subject2 = subject2;
	}

	public int getSubject3() {
		return subject3;
	}

	public void setSubject3(int subject3) {
		this.subject3 = subject3;
	}

	public int getSubject4() {
		return subject4;
	}

	public void setSubject4(int subject4) {
		this.subject4 = subject4;
	}

	public int getSubject5() {
		return subject5;
	}

	public void setSubject5(int subject5) {
		this.subject5 = subject5;
	}

	public int getSubject6() {
		return subject6;
	}

	public void setSubject6(int subject6) {
		this.subject6 = subject6;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
