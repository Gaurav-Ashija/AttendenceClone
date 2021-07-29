package com.sts.attendenceapp.services.audit;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.entities.Login;
import com.sts.attendenceapp.repositories.EmployeeRepository;
import com.sts.attendenceapp.repositories.LoginRepository;

@Service
public class ImplAudit implements IAudit {

	@Autowired
 	private EmployeeRepository employeeRepo;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	public Login login(Employee employee, HttpServletRequest request, HttpServletResponse response) {
		
		Login login = new Login();
		login.setBrowser(request.getHeader("USER-AGENT"));
		login.setLoginDate(new Date());
     	login.setLoginIp(request.getRemoteAddr());
     	login.setEmployee(employee);
     	login.setLoginActive(true);
     	login = loginRepository.save(login);
     	System.out.println("Login Details Inserted Successfully");
     	
		return login;
	}

	@Override
	public Login logout(Employee employee, Login login, HttpServletRequest request, HttpServletResponse response) {
		
		login.setBrowser(request.getHeader("USER-AGENT"));
		login.setLastLoginDate(new Date());
     	login.setLastLoginIp(request.getRemoteAddr());
     	login.setEmployee(employee);
     	login.setLoginActive(false);
     	login = loginRepository.save(login);
     	System.out.println("Login Details Updated Successfully");
     	
		return login;
	}

}
