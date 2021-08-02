package com.sts.attendenceapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.repositories.EmployeeRepository;
import com.sts.attendenceapp.services.admin.ImplAdmin;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private EmployeeRepository employeeRepo;

	@SuppressWarnings("null")
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		CustomUserDetails customUserDetails = null;
 			customUserDetails= IsUserExits(username);
 			return customUserDetails;
 	}
	
	public CustomUserDetails IsUserExits(String username) throws UsernameNotFoundException ,BadCredentialsException
	{
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
