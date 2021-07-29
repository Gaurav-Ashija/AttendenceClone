package com.sts.attendenceapp.services.audit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.entities.Login;

public interface IAudit {
	
    public Login login(Employee employee,HttpServletRequest request,HttpServletResponse response);
    
    public Login logout(Employee employee,Login login,HttpServletRequest request,HttpServletResponse response);
     
}
