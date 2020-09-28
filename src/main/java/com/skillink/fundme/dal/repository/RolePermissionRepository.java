package com.skillink.fundme.dal.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.skillink.fundme.dal.entity.RolePermission;



public interface RolePermissionRepository extends JpaRepository<RolePermission, Long>{

	RolePermission getById(long id);
	
}
