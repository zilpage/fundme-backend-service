package com.skillink.fundme.dal.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.skillink.fundme.dal.entity.Role;



public interface RoleRepository extends JpaRepository<Role, Long>{

	Role getById(long id);
	Role getByName(String name);
	
}
