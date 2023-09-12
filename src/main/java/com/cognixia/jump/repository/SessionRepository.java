package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.UserTask;

@Repository
public interface SessionRepository extends JpaRepository<UserTask, Integer>{
	
}
