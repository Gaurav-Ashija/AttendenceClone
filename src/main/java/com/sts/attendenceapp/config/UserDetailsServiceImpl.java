package com.sts.attendenceapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.repositories.EmployeeRepository;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Employee employee = employeeRepo.getUserByUserName(username);
		
		if(employee == null)
		{
			throw new UsernameNotFoundException("User Not Found");
		}
		else
		{
			  if(!employee.isPasswordReset())
  		       {
				  throw new BadCredentialsException("Please Check Username and Password");
    		   }
		}
		
		
		CustomUserDetails customUserDetails = new CustomUserDetails(employee);
		
		return customUserDetails;
	}

}
