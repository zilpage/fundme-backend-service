package com.skillink.fundme.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.skillink.fundme.dal.entity.Permission;
import com.skillink.fundme.dal.entity.Role;
import com.skillink.fundme.dto.Response;
import com.skillink.fundme.dto.Result;
import com.skillink.fundme.exception.BadRequestException;
import com.skillink.fundme.exception.DuplicateException;
import com.skillink.fundme.service.PermissionService;
import com.skillink.fundme.service.RoleService;

@RestController
@RequestMapping(value = "/api/v1/skilllink")
public class RoleController {

	@Autowired
	PermissionService permissionService;

	@Autowired
	RoleService roleService;

	
	@Autowired
	JdbcTemplate jdbcTemplate;

	
	@RequestMapping(value = "/roles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Response addRoles(@RequestBody @Validated Role role, HttpServletRequest request) throws Exception {

		if (role.getName() == null || role.getName().isEmpty())
			throw new BadRequestException("400", "Role name cannot be empty");

		
		Role roleDb = roleService.getRoleByName(role.getName());
		if(roleDb != null)
			throw new BadRequestException("400", "Role name already exist.");
		
		long id = roleService.create(role);
		Response response = new Response();
		response.setId(id);
		
		return response;
	}
	

	@RequestMapping(value = "/roles/{roleId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('UPDATE_ROLE')")
	public Response updateRoles(@RequestBody @Validated Role role, HttpServletRequest request, @PathVariable int roleId) throws Exception {

		if (roleId == 0)
			throw new BadRequestException("400", "Role ID cannot be empty");

		if (role == null) {
			throw new BadRequestException("400", "Role is empty");
		}

		if (role.getName() == null || role.getName().isEmpty())
			throw new BadRequestException("400", "Role name cannot be empty");

		
		Role roleInDb = roleService.getRoleById(roleId);
		if (roleInDb == null) {
			throw new BadRequestException("400", "Role does not exist");
		}

		Role roleNameDb = roleService.getRoleByName(role.getName().trim());
		if (roleNameDb != null) {
			throw new DuplicateException("409", "Role name already exist");
		}

		role.setId(roleId);
		long id = roleService.update(role);
		Response response = new Response();
		response.setId(id);
		return response;
	}

	

	@RequestMapping(value = "/roles/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Role role(@PathVariable int roleId) throws Exception {

		if (roleId == 0)
			throw new BadRequestException("400", "Role ID cannot be empty");
		
		Role role = roleService.getRoleById(roleId);
		if (role == null) {
			throw new BadRequestException("400", "Role does not exist");
		}

		return role;
	}

	@RequestMapping(value = "/rolepermissions/{roleId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void assignPermissionsToRole(@RequestBody @Validated List<Integer> permissions, @PathVariable int roleId,
			HttpServletRequest request) throws Exception {

		if (roleId == 0)
			throw new BadRequestException("400", "Role ID cannot be empty");

		if (permissions == null) {
			throw new DuplicateException("409", " Empty request body");
		}
		
		Role role = roleService.getRoleById(roleId);
		if (role == null) {
			throw new BadRequestException("400", "Role does not exist");
		}
		
		roleService.deleteAssignedPermissionsToRoleByRoleId(roleId);

		for (int i = 0; i < permissions.size(); i++) {
			Permission perm = permissionService.getPermissionById(permissions.get(i));
			if (perm != null) {

				 String sql = "INSERT IGNORE INTO tbl_role_permission (role_id, permission_id) values (?, ?)";
				   jdbcTemplate.update(sql, roleId, permissions.get(i));

			}

		}
	}


	

	@RequestMapping(value = "/rolepermissions/{roleId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Permission> getAllAssignedPermissionsToRoleByRoleId(@PathVariable int roleId) throws Exception {

		if (roleId == 0)
			throw new BadRequestException("400", "Role ID cannot be empty");

		Role role = roleService.getRoleById(roleId);
		if (role == null) {
			throw new BadRequestException("400", "Role does not exist");
		}
		
		List<Permission> list = permissionService.getPermissionsAssignedToRole(roleId);
		
		return list;
	}

	@RequestMapping(value = "/roles", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Result<Role> getAllRoles() throws Exception {
		Result<Role> role = new Result<Role>();
		List<Role> roles = roleService.getAllRoles();
		role.setList(roles);
		return role;

	}
	
	@RequestMapping(value = "/permissions", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@PreAuthorize("hasAuthority('READ_ALL_PERMISSIONS')")
	public List<Permission> getAllPermissions() throws Exception {

		return permissionService.getAllPermissions();
	}
}
