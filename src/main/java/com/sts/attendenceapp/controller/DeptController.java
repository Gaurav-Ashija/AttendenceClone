package com.sts.attendenceapp.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Object> addRole(@RequestBody Department dept)
	{
		Department departments= deptRepo.findBydeptName(dept.getDeptName());
		boolean isCreated=false;
		Department department = null;
		
			if(departments!=null && departments.getDeptName().equals(dept.getDeptName()))
			{
				isCreated=true;
			}
		
		if(!isCreated)
		{
 		
 		Department d= deptRepo.save(dept);
		    return new ResponseEntity<Object>(d, HttpStatus.OK);
		}
	    return new ResponseEntity<Object>("Role Already Created", HttpStatus.BAD_REQUEST);
	    
	     
 	
	}	
}
