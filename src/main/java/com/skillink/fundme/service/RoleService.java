package com.skillink.fundme.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.skillink.fundme.dal.entity.Permission;
import com.skillink.fundme.dal.entity.Role;
import com.skillink.fundme.dal.repository.RoleRepository;

@Service
public class RoleService {

	private final static Logger logger = LoggerFactory.getLogger(RoleService.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	RoleRepository roleRepository;

	public long create(Role role) throws Exception

	{
		try {
			Role rol = roleRepository.save(role);
			return rol.getId();
		} catch (Exception ex) {
			throw ex;
		}
	}

	public long update(Role role) throws Exception

	{
		try {
			Role rol = roleRepository.save(role);
			return rol.getId();
		} catch (Exception ex) {
			throw ex;
		}
	}

	public Role getRoleByName(String name) throws Exception {
		try {
			Role res = null;
			String sql = "SELECT * FROM tbl_role  where name = ? ";

			RowMapper<Role> rowMapper = new BeanPropertyRowMapper<Role>(Role.class);
			List<Role> list = jdbcTemplate.query(sql, rowMapper, name);
			if (list.size() > 0) {
				res = list.get(0);
			}
			return res;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public Role getRoleById(long roleId) throws Exception {
		try {
			Role res = null;
			String sql = "SELECT * FROM tbl_role  where id = ? ";

			RowMapper<Role> rowMapper = new BeanPropertyRowMapper<Role>(Role.class);
			List<Role> list = jdbcTemplate.query(sql, rowMapper, roleId);
			if (list.size() > 0) {
				res = list.get(0);

				sql = "SELECT p.* FROM tbl_role_permission rp inner join tbl_permission p on p.id = rp.permission_id   where rp.role_id = ?";

				RowMapper<Permission> rowMapperr = new BeanPropertyRowMapper<Permission>(Permission.class);
				List<Permission> permissions = jdbcTemplate.query(sql, rowMapperr, roleId);
				res.setPermissions(permissions);

			}
			return res;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public void deleteAssignedPermissionsToRoleByRoleId(long roleId) throws Exception {

		String sql = "DELETE from tbl_role_permission where role_id = ?";
		jdbcTemplate.update(sql, roleId);

	}

	public List<Role> getAllRoles() throws Exception {
		try {

			List<Role> roles = new ArrayList<Role>();

			roles = roleRepository.findAll();
			return roles;
		} catch (Exception ex) {
			throw ex;
		}
	}

}
