package com.sts.attendenceapp.services.admin;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sts.attendenceapp.entities.ConfirmationToken;
import com.sts.attendenceapp.entities.Department;
import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.entities.Mail;
import com.sts.attendenceapp.entities.Role;
import com.sts.attendenceapp.repositories.ConfirmationTokenRepository;
import com.sts.attendenceapp.repositories.DepartmentRepository;
import com.sts.attendenceapp.repositories.EmployeeRepository;
import com.sts.attendenceapp.repositories.RoleRepository;
import com.sts.attendenceapp.services.EmailService;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
 public class ImplAdmin implements IAdmin {

	@Autowired
	RoleRepository rolerepo;
	
	@Autowired
	DepartmentRepository departmentrepo;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
		
	@Autowired
	private EmailService emailService;
		
	@Override
	public Employee oneAdmin(Employee emp) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, MessagingException, IOException, TemplateException {
		
 	 	  //emp.setCreatedBy(emp.getId());	
  		  emp.setCreatedDate(new Date());
		  emp.setActive(true);
		  //emp.setActiveById(emp.getId());
		  emp.setActiveDate(new Date());
		  emp.setDeleted(false);
		  emp.setPasswordReset(false);
		  String password = "admin";
		  emp.setPassword(passwordEncoder.encode(password));

		
		 	Role role=new Role();
		 	role.setRoleName("ROLE_ADMIN");
			role.setRoleDescription("This is Admin Role");
			
		 	Department department=new Department();
			department.setDeptName("Administrator");
			department.setDeptDescription("This is Administrator Department");
			Role role2= rolerepo.save(role);
		 	Department department2= departmentrepo.save(department);
		 	
		 	emp.setRole(role2);
		 	emp.setDepartment(department2);
		 	Employee employee= employeeRepo.save(emp);
		 	
		 	System.out.print("employee" + employee);
		 	
		 	Role role3= employee.getRole();
		 	List<Employee> employees= role.getEmployee();		
		 	employees.add(employee);
		 	rolerepo.save(role3);
		 	
		 	Department department3=employee.getDepartment();
		 	List<Employee> deEmployees= department3.getEmployee();		
		 	deEmployees.add(employee);
		 	departmentrepo.save(department3);
		 	
		 	ConfirmationToken confirmationToken = new ConfirmationToken(emp);
		    confirmationTokenRepository.save(confirmationToken);
		
				   	Mail mail = new Mail();
		 	        mail.setTo(emp.getEmail());
			        mail.setSubject("Registration Done");
			        mail.setUUID(confirmationToken.getConfirmationToken());
			        String userName = emp.getFirstName()+" "+emp.getLastName();
		
			        emailService.sendEmail(mail,userName);
			        System.out.println("Email Sended Successfully");
		 	
 	return employee;
 	}

}
