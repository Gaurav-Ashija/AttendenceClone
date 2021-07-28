package com.sts.attendenceapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sts.attendenceapp.entities.Login;

public interface LoginRepository extends JpaRepository<Login, Integer> {

	 public Login findByemployee(String email); 
	
}


