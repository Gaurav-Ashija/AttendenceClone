package com.sts.attendenceapp.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

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

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

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
	
	@Autowired
	private EmailService emailService;
	
	 @Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Bean 
	public FreeMarkerConfigurer freemarkerClassLoaderConfig() {
	    Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
	    TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), "/mailtemplate/");
	    configuration.setTemplateLoader(templateLoader);
	    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
	    freeMarkerConfigurer.setConfiguration(configuration);
	    return freeMarkerConfigurer; 
	}

	
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
			@RequestParam("roleId") String role,@RequestParam("departmentId") String dept) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, MessagingException, IOException, TemplateException 
	{
		
	  if(result.hasErrors())
	  {
		  model.addAttribute("employee", emp);
		  return "register";
	  }
		
	  
	  try {
		
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
	  
	  Employee employee = null;
	 
		   employee = employeeRepo.save(emp);
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
	   
	   ConfirmationToken confirmationToken = new ConfirmationToken(emp);
       confirmationTokenRepository.save(confirmationToken);

		   	Mail mail = new Mail();
 	        mail.setTo(emp.getEmail());
	        mail.setSubject("Registration Done");
	        mail.setUUID(confirmationToken.getConfirmationToken());
	        String userName = emp.getFirstName()+" "+emp.getLastName();

	        emailService.sendEmail(mail,userName);
	        System.out.println("Email Sended Successfully");
 	  
		} catch (Exception e) {
			System.out.println("Exception : "+ e);
			
		}

	  return "register";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model)
	{	
		return "dashboard";
	}
	
	//Handler for Custom Login
	@GetMapping("/signin")
	public String customLogin()
	{    	 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
		
		return "redirect:/";
	}
	
	//Handler for Reset Password
	@GetMapping("/resetpassword")
	public String resetpassword(@RequestParam("token") String confirmationToken,Model model) 
     {
		System.out.println("Token is " + confirmationToken);
		
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token != null)
        {
        	Employee employee= employeeRepo.findByemail(token.getEmployee().getEmail());
         	model.addAttribute("email", employee.getEmail());
         	
        }
        
        return "resetpassword";
    }
	
	//Handler for Reset Password
	    @PostMapping("/reset")
		public String reset(@RequestParam("employee-email") String email,
				@RequestParam("password") String password,
				@RequestParam("confirm-password") String confirmPassword,Model model) 
	     {
	       Employee employee = null;
	    
	       if(email != null)
	       {
	    	   employee = employeeRepo.findByemail(email);
	    	   if(employee != null)
	    	   {
	    		   if(!employee.isPasswordReset())
	    		   {
	    			   employee.setPassword(passwordEncoder.encode(password));
	    			   employee.setPasswordReset(true);
	    			   employeeRepo.save(employee);
	    			   model.addAttribute("reset_success", "Password Reset Successfully");
	    		   }
	    		   else
	    		   {
	    			   model.addAttribute("reset_failure", "You Have Already Reset Your Password");
	    		   }
	    	   }
	    	   
	       }
	    
	    	return "resetpassword";
	     }
	
}
