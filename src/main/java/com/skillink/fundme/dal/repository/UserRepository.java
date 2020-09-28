package com.skillink.fundme.dal.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.skillink.fundme.dal.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{

	User getById(long id);
	User getByUsername(String username);
}
