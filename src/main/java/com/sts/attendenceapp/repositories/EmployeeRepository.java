package com.sts.attendenceapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.attendenceapp.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query("select e from Employee e where e.email =:email")
	public Employee getUserByUserName(@Param("email") String email);
	
	public Employee findByemail(String email);

	
}
