package com.sts.attendenceapp.services.attendence;

import java.util.Date;

import com.sts.attendenceapp.entities.Attendence;
import com.sts.attendenceapp.entities.Employee;

public interface IAttendence {

	public Attendence findbypunchDate(Date today);
	
	public Attendence punchin(Employee employee,String punchin,String punchInLocation);
	
	public Attendence punchout(Attendence attendence, Employee employee,String punchout,String punchOutLocation);
	
}
