package com.sts.attendenceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sts.attendenceapp.entities.Department;
import com.sts.attendenceapp.repositories.DepartmentRepository;

@RestController
public class DeptController {

	@Autowired	
	DepartmentRepository deptRepo;	
	
	@PostMapping("/dept/add")
	public Department addRole(@RequestBody Department dept)
	{
		deptRepo.save(dept);
		return dept;
	}
	
}
