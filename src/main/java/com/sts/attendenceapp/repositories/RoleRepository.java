package com.sts.attendenceapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.sts.attendenceapp.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
