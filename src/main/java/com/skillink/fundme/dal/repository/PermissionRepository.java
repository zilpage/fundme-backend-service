package com.skillink.fundme.dal.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.skillink.fundme.dal.entity.Permission;



public interface PermissionRepository extends JpaRepository<Permission, Long>{

	Permission getById(long id);
	
}
