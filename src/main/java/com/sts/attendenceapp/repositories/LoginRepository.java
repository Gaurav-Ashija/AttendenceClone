package com.sts.attendenceapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.entities.Login;

public interface LoginRepository extends JpaRepository<Login, Integer> {

	 public List<Login> findByemployee(Employee employee); 
	
}


