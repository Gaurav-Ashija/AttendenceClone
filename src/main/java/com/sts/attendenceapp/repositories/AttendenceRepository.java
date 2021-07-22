package com.sts.attendenceapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sts.attendenceapp.entities.Attendence;

public interface AttendenceRepository extends JpaRepository<Attendence, Integer> {


}
