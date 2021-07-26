package com.sts.attendenceapp.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sts.attendenceapp.entities.Attendence;
import com.sts.attendenceapp.entities.ConfirmationToken;
import com.sts.attendenceapp.entities.Employee;

@Repository
public interface AttendenceRepository extends JpaRepository<Attendence, Integer> {

	  public Attendence findBypunchDate(Date today);
	  
	  public Attendence findByemployee(String  username);

}
