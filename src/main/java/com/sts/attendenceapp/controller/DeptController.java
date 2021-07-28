package com.sts.attendenceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.attendenceapp.entities.Department;
import com.sts.attendenceapp.repositories.DepartmentRepository;

@RestController
@RequestMapping("/dept")
public class DeptController {

	@Autowired	
	DepartmentRepository deptRepo;	
	
 	@PostMapping("/addDept")
	public ResponseEntity<Object> addDept(@RequestBody Department dept)
	{
		Department departments= deptRepo.findBydeptName(dept.getDeptName());
		boolean isCreated=false;
		if(departments!=null && departments.getDeptName().equals(dept.getDeptName()))
			{
				isCreated=true;
			}
		
		if(!isCreated)
		{
 		Department d= deptRepo.save(dept);
		    return new ResponseEntity<Object>(d, HttpStatus.CREATED);
		}
	    return new ResponseEntity<Object>("Role Already Created", HttpStatus.BAD_REQUEST);
	}
	
//	@PreAuthorize("hasRole('ROLE_USER')")
 	@GetMapping("/getDepts")
	public ResponseEntity<Object> getDepts(Authentication authentication)
	{
   		List<Department>departments= deptRepo.findAll();
 		if(!(departments==null))
		{
  		    return new ResponseEntity<Object>(departments, HttpStatus.OK);
  		}
		    return new ResponseEntity<Object>("No Role Created", HttpStatus.BAD_REQUEST);
	}
}
