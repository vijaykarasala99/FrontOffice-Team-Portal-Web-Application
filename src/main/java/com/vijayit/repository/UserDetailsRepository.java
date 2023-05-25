package com.vijayit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijayit.entity.UserDetailsEntity;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer> {

	public UserDetailsEntity findBystaffEmail(String staffEmail);

	public UserDetailsEntity findBystaffEmailAndNewPwd(String staffEmail, String newPwd);


}
