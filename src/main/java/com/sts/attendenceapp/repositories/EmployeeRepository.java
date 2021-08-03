package com.sts.attendenceapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sts.attendenceapp.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query("select e from Employee e where e.email =:email")
	public Employee getUserByUserName(@Param("email") String email);
	
	public Employee findByemail(String email);

	public List<Employee> findBydesignation(String designation);
	
}
