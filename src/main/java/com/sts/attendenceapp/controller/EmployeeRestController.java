package com.sts.attendenceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.repositories.EmployeeRepository;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

	@Autowired
	private EmployeeRepository employeeRepo;
	
	@GetMapping("/getemployees")
	public ResponseEntity<List<Employee>> getEmployees()
	{
  		return new ResponseEntity<List<Employee>>(employeeRepo.findAll(), HttpStatus.OK);
 	}
}
