package com.sts.attendenceapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sts.attendenceapp.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
