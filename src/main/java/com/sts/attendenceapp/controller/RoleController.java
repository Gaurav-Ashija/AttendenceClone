package com.sts.attendenceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sts.attendenceapp.entities.Role;
import com.sts.attendenceapp.repositories.RoleRepository;

@RestController
public class RoleController {
	
	@Autowired	
	RoleRepository roleRepo;	
	
	@PostMapping("/role/add")
	public Role addRole(@RequestBody Role r)
	{
		roleRepo.save(r);
		return r;
	}

}
