package com.sts.attendenceapp.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sts.attendenceapp.entities.Role;
import com.sts.attendenceapp.repositories.RoleRepository;

@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleRepository roleRepo;	
	
 	@RequestMapping(value = "/addRole", method = RequestMethod.POST, consumes="application/json")
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
 		    return new ResponseEntity<Object>(role, HttpStatus.CREATED);
  		}
		    return new ResponseEntity<Object>("Role Already Created", HttpStatus.BAD_REQUEST);
	}
	
	//@PreAuthorize("hasRole('ROLE_USER')")
 	@GetMapping("/getRoles")
	public ResponseEntity<Object> getRoles()
	{
   		List<Role>roleObj= roleRepo.findAll();
   		if(!(roleObj==null))
		{
  		    return new ResponseEntity<Object>(roleObj, HttpStatus.OK);
  		}
		    return new ResponseEntity<Object>("No Role Created", HttpStatus.BAD_REQUEST);
	}
 	
}
