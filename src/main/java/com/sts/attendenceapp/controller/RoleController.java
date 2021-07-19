package com.sts.attendenceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sts.attendenceapp.entities.Department;
import com.sts.attendenceapp.entities.Role;
import com.sts.attendenceapp.repositories.RoleRepository;

@RestController
public class RoleController {
	
	@Autowired	
	RoleRepository roleRepo;	
	
	@PostMapping("/role/add")
	public ResponseEntity<Object> addRole(@RequestBody Role r)
	{
 		String roleName=r.getRoleName();
  		Role roleObj= roleRepo.findByroleName(roleName);
  		
		boolean isCreated=false;
  		
			if( roleObj!=null && roleObj.getRoleName().equals(roleName))
			{
				isCreated=true;
			}
		
		if(!isCreated)
		{
			Role role=roleRepo.save(r);
 		    return new ResponseEntity<Object>(role, HttpStatus.OK);
  		}
		    return new ResponseEntity<Object>("Role Already Created", HttpStatus.BAD_REQUEST);
	}

}
