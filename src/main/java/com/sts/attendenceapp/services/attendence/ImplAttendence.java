package com.sts.attendenceapp.services.attendence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sts.attendenceapp.entities.Attendence;
import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.repositories.AttendenceRepository;
import com.sts.attendenceapp.repositories.EmployeeRepository;
import com.sts.attendenceapp.repositories.RoleRepository;

@Service
@Transactional
public class ImplAttendence implements IAttendence {

	@Autowired
	private AttendenceRepository attendenceRepository;

	@Autowired
 	EmployeeRepository employeeRepo;

 	@Override
	public Attendence findbypunchDate(Date today) {
		return null;
	} 

	@Override
	public Attendence punchin(Employee employee,String punchin) {
		
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
 	    String strDate = formatter.format(new Date());  

        Date date = null;
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

 		Attendence attendence=new Attendence();
		attendence.setPunchDate(date);
		attendence.setPunchIn(punchin);
		attendence.setEmployee(employee);
		attendence.setPunchTimes(1);
		System.out.println("attendenceRepository" + attendenceRepository);
 		Attendence a= attendenceRepository.save(attendence);
 		
 		 List<Attendence> attendences=employee.getAttendence();
 		 attendences.add(a);
 		 employee.setAttendence(attendences);
 		 employeeRepo.save(employee);
 		
		return a;
	}
	
	@Override
	public Attendence punchout(Attendence attendence, Employee employee,String punchout) {
	
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		
		 // Parsing the Time Period
       Date date1 = null,date2 = null;
		try {
			date1 = simpleDateFormat.parse(attendence.getPunchIn());
	        date2 = simpleDateFormat.parse(punchout);

		} catch (ParseException e) {
			e.printStackTrace();
		}
  
		 // Calculating the difference in milliseconds
       long differenceInMilliSeconds
           = Math.abs(date2.getTime() - date1.getTime());
 
       // Calculating the difference in Hours
       long differenceInHours
           = (differenceInMilliSeconds / (60 * 60 * 1000))
             % 24;
 
       // Calculating the difference in Minutes
        long differenceInMinutes
       = (differenceInMilliSeconds / (60 * 1000)) % 60;

		
        String duration=String.valueOf(differenceInHours)+ " Hours "+ String.valueOf(differenceInMinutes) + " Min ";
        attendence.setWorkDuration(duration);
		
  		attendence.setPunchOut(punchout);
		attendence.setEmployee(employee);
		attendence.setPunchTimes(2);
 		System.out.println("attendenceRepository" + attendenceRepository);
		Attendence a= attendenceRepository.save(attendence);

 		List<Attendence> attendences=employee.getAttendence();
 		attendences.add(a);
 		employee.setAttendence(attendences);
 		employeeRepo.save(employee);
 		
		return a;
	}
	
}
