package com.saish.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saish.crud.dto.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser, Integer> {

	boolean existsByEmail(String email);

	MyUser findByEmail(String email);

}