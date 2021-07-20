package com.sts.attendenceapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sts.attendenceapp.entities.ConfirmationToken;
 
public interface ConfirmationTokenRepository extends JpaRepository <ConfirmationToken, Long> {

  public ConfirmationToken findByConfirmationToken(String confirmationToken);

}
