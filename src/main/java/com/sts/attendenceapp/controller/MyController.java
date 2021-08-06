package com.sts.attendenceapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sts.attendenceapp.entities.Attendence;
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
import com.sts.attendenceapp.services.attendence.ImplAttendence;

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
	private ImplAttendence implAttendence;

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

	//Handler for Home page
    @GetMapping("/")
	 public String home(Model model)
	 {
   	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	 if (authentication != null) {
   		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
    	        Date date = null;
    			try {
    		 	      String strDate = formatter.format(new Date());  
    		 	      date = formatter.parse(strDate);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  

     				 String username = authentication.getName();
		    		 Employee employee = employeeRepo.findByemail(username);
		    		 List<Attendence> attendences=employee.getAttendence();
	    			 System.out.println("attendences :" + attendences);

		    		 Attendence currentPunchDate=null;
		    		 for (Attendence attendence : attendences) {
						Date punchDate= attendence.getPunchDate();
		    			 System.out.println("attendences2 :" + attendence.getPunchDate());

						if(date.equals(punchDate))
						{
							currentPunchDate=attendence;
						}
					}
		    		 
 	 	     		 model.addAttribute("user", employee);
		    		 model.addAttribute("attendence", currentPunchDate);
   		 
		    		 return "home";
        }
   	 else
   	 {
   		 		return "login";
   	 }
		 
	 }
	
	//Handler for Registration
	@GetMapping("/register")
	public String register(Model model)
	{	
		model.addAttribute("employee", new Employee());
		
		List<Role> roles = roleRepo.findAll();
		List<Department> depts = deptRepo.findAll();
		List<Employee> employees = employeeRepo.findAll();
		model.addAttribute("empRole", roles);
		model.addAttribute("empDepartment", depts);
		model.addAttribute("employees", employees);
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		   	 if (authentication != null) {
		   		 String username = authentication.getName();
		   		 Employee employee = employeeRepo.findByemail(username);
		   		 model.addAttribute("user", employee);
		   		 return "register";
		        }
		   	 else
		   	 {
		   		 return "login";
		   	 }
		
	}
	
	//Handler for Registration
	@PostMapping("/do_register")
	public String registration(@Valid @ModelAttribute("employee") Employee emp,BindingResult result,Model model,
			@RequestParam("roleId") String role,@RequestParam("departmentId") String dept) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, MessagingException, IOException, TemplateException 
	{

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 
	  if(authentication == null) 
	   {
			return "login";
	   }
	  else
	  {	  
		  String username = authentication.getName();
	   	  Employee authEmployee = employeeRepo.findByemail(username);
	   	  model.addAttribute("user", authEmployee); 
		
		  if(result.hasErrors())
		  {
			  model.addAttribute("employee", emp);
			  return "register";
		  }
		  else
		  {	  
	   
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
		  
		  //String designation = emp.getDesignation().toUpperCase();
		  String headEmail = emp.getHeadEmail();
		  String fullName = headEmail.substring(0, headEmail.indexOf("@"));
		  String headName = null;
		  
		  if(fullName.contains("."))
		  {
		      headName = fullName.replace(".", " ");
		  }
		  else
		  {
			  headName = fullName;
		  }
		  //emp.setDesignation(designation);
		  emp.setHeadName(headName);
		  
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
		        
		        model.addAttribute("employee", new Employee());
		        model.addAttribute("registration_success", "Registration Completed Successfully");
	 	  
			} catch (Exception e) {
				System.out.println("Exception : "+ e);
				
				model.addAttribute("employee", emp);
				model.addAttribute("registration_failure", "Registration Failed");
				
			}
		  
		     return "register";
		  }
	  }	  
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model)
	{	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
   	   if (authentication != null) {
   		 String username = authentication.getName();
   		 Employee employee = employeeRepo.findByemail(username);
   		 model.addAttribute("user", employee);
   		 return "dashboard";
        }
   	    else
   	    {
   		 return "login";
   	    }
		
	}
	
	//Handler for Custom Login
	@GetMapping("/signin")
	public String customLogin()
	{    	 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
		
		return "redirect:/dashboard";
	}
	
	@GetMapping("/hierarchy")
	public String hierarchy(Model model) throws JsonProcessingException
	{   
		int counter = 1; 
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	   	 if (authentication != null) {
	   		 String username = authentication.getName();
	   		 Employee employee = employeeRepo.findByemail(username);
	   		 String json = null;
	   	 
	   		 if(employee != null)
	   		 {
	   			 String fullName = employee.getFirstName() +" "+ employee.getLastName();
	   			 ObjectMapper mapper = new ObjectMapper();
	   			 ObjectNode json1 = mapper.createObjectNode();
  				 json1.put("name", fullName);
  				 json1.put("designation", employee.getDesignation());
	   			 
	   				 Employee head = employeeRepo.findByemail(employee.getHeadEmail());
	   				 while(head != null)
	   				 {
	   				    String headName = head.getFirstName() +" "+head.getLastName();	 
	   					ObjectNode json2 = mapper.createObjectNode();
	   					json2.put("name", headName);
	   					json2.put("designation", head.getDesignation());
	   					json1.set("head"+counter, json2);
	   					
	   					head = employeeRepo.findByemail(head.getHeadEmail());
	   					counter++;  
	   				 }
	   				 
	   				json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
	   				System.out.println(json);
	   		 }
	   		
	   		 model.addAttribute("user", employee);
	   		 model.addAttribute("hierarchy", json);
	   		 return "hierarchy";
	        }
	   	 else
	   	 {
	   		 return "login";
	   	 }
		
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
	    
 		@RequestMapping(value="/punchtime", method=RequestMethod.POST)
  	    public @ResponseBody Attendence punchtime(@RequestParam("punchTime") String punchTime,@RequestParam("location") String location,Model model) {
    	
 			String username = isUserLogin();
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
 	        Date date = null;
 	        Attendence a=null;
 	       try {
  		 	      String strDate = formatter.format(new Date());  
 		 	      date = formatter.parse(strDate);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
 	       

	    	 if (username != null) {
   	    		 Employee employee = employeeRepo.findByemail(username);
	    		 List<Attendence> attendences=employee.getAttendence();
    			 System.out.println("attendences :" + attendences);

	    		 Attendence currentAttendencepunch=null;
	    		 for (Attendence attendence : attendences) {
						Date punchDate= attendence.getPunchDate();
		    			 System.out.println("attendences2 :" + attendence.getPunchDate());

					if(date.equals(punchDate))
					{		    			 
						System.out.println("current attendence :" + attendence.getPunchDate());

						currentAttendencepunch=attendence;
					}
				}
					System.out.println("currentAttendencepunch :" + currentAttendencepunch);

 	    		 model.addAttribute("user", employee);
	    		 model.addAttribute("attendence", currentAttendencepunch);
	    		 if(currentAttendencepunch!=null)
	    		 { 
		    		 a= implAttendence.punchout(currentAttendencepunch,employee,punchTime,location);
  	    		 }
	    		 else
	    		 {
 		    		  a= implAttendence.punchin(employee,punchTime,location);
	    		 }
  	         }
  	         
			return a;	
   	    }
		 
 		@RequestMapping(value="/headInfo", method=RequestMethod.POST)
 		@ResponseBody
 		public void getHeadInfo(@RequestParam("headDesignation") String headDesignation,HttpServletRequest req,HttpServletResponse res) throws IOException
 		{
 			res.setContentType("text/html");
		    PrintWriter writer = res.getWriter();
 		    List<Employee> employees = null;	
 		    System.out.println(headDesignation);
 		    employees = employeeRepo.findBydesignation(headDesignation); 
 		    System.out.println(employees);
 		    TreeSet<String> set1 = new TreeSet<>();
 		    if(employees != null)
 		    {
 		    	for(Employee employee : employees)
	              {
      			    set1.add(employee.getEmail()); 
	              }
 		    	writer.print(set1.toString());
	    		writer.flush(); 
 		    }
 		   
 		}
 		
		public String isUserLogin()
		{
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}
	
  	
}
