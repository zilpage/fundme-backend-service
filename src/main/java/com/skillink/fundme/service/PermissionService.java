package com.skillink.fundme.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.skillink.fundme.dal.entity.Permission;
import com.skillink.fundme.dal.entity.Role;
import com.skillink.fundme.dal.repository.PermissionRepository;
import com.skillink.fundme.dal.repository.RoleRepository;

@Service
public class PermissionService {

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	JdbcTemplate jdbcTemplate;

	public long addPermission(Permission perm) throws Exception

	{
		try {
			Permission permission = permissionRepository.save(perm);
			return permission.getId();
		} catch (Exception ex) {
			throw ex;
		}
	}

	

	public List<Permission> getAllPermissions() throws Exception {
		try {
			List<Permission> res = new ArrayList<Permission>();
			res = permissionRepository.findAll();
			return res;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public Permission getPermissionById(int permissionId) throws Exception {
		try {
			Permission res = null;
			res = permissionRepository.getById(permissionId);
			return res;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public List<Permission> getPermissionsAssignedToRole(int roleId) {
		
		String sql = "SELECT p.* FROM tbl_role_permission rp left join tbl_permission p on p.id = rp.permission_id   where rp.role_id = ?";

		RowMapper<Permission> rowMapper = new BeanPropertyRowMapper<Permission>(Permission.class);
		List<Permission> list = jdbcTemplate.query(sql, rowMapper, roleId);
		return list;
	}
}
