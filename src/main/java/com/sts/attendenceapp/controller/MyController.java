package com.sts.attendenceapp.controller;

import java.util.Date;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sts.attendenceapp.entities.Department;
import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.entities.Role;
import com.sts.attendenceapp.repositories.DepartmentRepository;
import com.sts.attendenceapp.repositories.EmployeeRepository;
import com.sts.attendenceapp.repositories.RoleRepository;

@Controller
public class MyController {
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private DepartmentRepository deptRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//Handler for Registration
	@GetMapping("/register")
	public String register(Model model)
	{	
		model.addAttribute("employee", new Employee());
		
		List<Role> roles = roleRepo.findAll();
		List<Department> depts = deptRepo.findAll();
		model.addAttribute("empRole", roles);
		model.addAttribute("empDepartment", depts);
		
		return "register";
	}
	
	//Handler for Registration
	@PostMapping("/do_register")
	public String registration(@Valid @ModelAttribute("employee") Employee emp,BindingResult result,Model model,
			@RequestParam("roleId") String role,@RequestParam("departmentId") String dept)
	{
		
	  if(result.hasErrors())
	  {
		  model.addAttribute("employee", emp);
		  return "register";
	  }
		
	  //emp.setCreatedBy(emp.getId());	
	  emp.setCreatedDate(new Date());
	  emp.setActive(true);
	  //emp.setActiveById(emp.getId());
	  emp.setActiveDate(new Date());
	  emp.setDeleted(false);
	  emp.setPasswordReset(false);
	  String password = "demo";
	  emp.setPassword(passwordEncoder.encode(password));
	  
	  int roleId = Integer.parseInt(role);
	  int deptId = Integer.parseInt(dept);
	  
	  Optional<Role> r = roleRepo.findById(roleId);
	  Role empRole = r.get();
	  Optional<Department> d = deptRepo.findById(deptId);
	  Department empDept = d.get();
	  
	  emp.setRole(empRole);
	  emp.setDepartment(empDept);
	  
	  Employee employee = employeeRepo.save(emp);
	  System.out.println("Employee Details Inserted Successfully");
	  
	  List<Employee> roleEmployeeList = empRole.getEmployee();
	  roleEmployeeList.add(employee);
 	  
	  List<Employee> departmentEmployeeList = empDept.getEmployee();
	  departmentEmployeeList.add(employee);
  	  
	  roleId = employee.getRole().getRoleId();
	  deptId = employee.getDepartment().getDeptId();
	  
	   r = roleRepo.findById(roleId);
	   empRole = r.get();
	   d = deptRepo.findById(deptId);
	   empDept = d.get();
	  
	   empRole.setEmployee(roleEmployeeList);
	   roleRepo.save(empRole);
	   System.out.println("Role Details Inserted Successfully");
	   empDept.setEmployee(departmentEmployeeList);
	   deptRepo.save(empDept);
	   System.out.println("Department Details Inserted Successfully");
	  
	  return "register";
	}
	
	//Handler for Custom Login
	@GetMapping("/signup")
	public String customLogin(Model model)
	{
		return "login";
	}
	
}
