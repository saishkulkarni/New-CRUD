package com.saish.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saish.crud.dto.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}