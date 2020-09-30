package com.skillink.fundme.api;

import com.skillink.fundme.dal.entity.Permission;
import com.skillink.fundme.dal.entity.Role;
import com.skillink.fundme.dal.entity.User;
import com.skillink.fundme.dto.JWTLoginSuccessResponse;
import com.skillink.fundme.dto.LoginRequest;
import com.skillink.fundme.exception.BadRequestException;
import com.skillink.fundme.security.IAuthenticationFacade;
import com.skillink.fundme.security.JwtTokenProvider;
import com.skillink.fundme.service.MapValidationErrorService;
import com.skillink.fundme.service.RoleService;
import com.skillink.fundme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.skillink.fundme.security.SecurityConstants.TOKEN_PREFIX;

/**
 * @author Rabiu Ademoh
 */

@RestController
@RequestMapping(value = "/api/v1/skilllink")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private RoleService roleService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    @RequestMapping( value = "users/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) throws Exception {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;
        User user = null;

        user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if(user == null) {
            throw new BadCredentialsException("Invalid Login details.");
        }

        if (!user.is_enabled()) {
            throw new BadRequestException("400", "You have been disabled. Kindly contact the administrator");
        }

        if (user.is_locked()) {
            throw new BadCredentialsException("You have been locked. Kindly contact the administrator");
        }


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        Role role = roleService.getRoleById(user.getRoleId());

        List<Permission> permissions = role.getPermissions();


        user.setPermissions(permissions);
        user.setRole(role);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(authentication);

        JWTLoginSuccessResponse jwtLoginSuccessResponse = new JWTLoginSuccessResponse(user.getLastLogin(),jwt,user.getUsername(),role,permissions);



        return ResponseEntity.ok(jwtLoginSuccessResponse);
    }



    @PostMapping("users/create")
    public User registerUser(@Valid @RequestBody User user, BindingResult result) throws Exception {

       // ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
       // if(errorMap != null)return errorMap;


        User newUser = userService.create(user);

        return  newUser;
      //  return  new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }
}
