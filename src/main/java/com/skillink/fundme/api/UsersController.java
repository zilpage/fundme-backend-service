package com.skillink.fundme.api;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.skillink.fundme.dal.entity.Permission;
import com.skillink.fundme.dal.entity.Role;
import com.skillink.fundme.dal.entity.User;
import com.skillink.fundme.dto.JWTLoginSuccessResponse;
import com.skillink.fundme.dto.LoginRequest;
import com.skillink.fundme.dto.Response;
import com.skillink.fundme.exception.BadRequestException;
import com.skillink.fundme.security.IAuthenticationFacade;
import com.skillink.fundme.security.JwtTokenProvider;
import com.skillink.fundme.service.MapValidationErrorService;
import com.skillink.fundme.service.RoleService;
import com.skillink.fundme.service.UserService;
import com.skillink.fundme.util.ConstantUtil;

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


//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername(),
//                        loginRequest.getPassword()
//                )
//        );

        Role role = roleService.getRoleById(user.getRoleId());
        List<Permission> permissions = null;
        if(role != null) {
        	permissions = role.getPermissions();

            user.setPermissions(permissions);
            user.setRole(role);
        }
        

//        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(String.valueOf(user.getId()), user.getUsername());

        JWTLoginSuccessResponse jwtLoginSuccessResponse = new JWTLoginSuccessResponse(user.getLastLogin(),jwt,user.getUsername(),role,permissions);
        jwtLoginSuccessResponse.setId(user.getId());
        jwtLoginSuccessResponse.setRole(role);
        jwtLoginSuccessResponse.setName(user.getFirstName()+" "+user.getLastName());


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
    
    
    
    
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	// @PreAuthorize("hasAuthority('CREATE_PARENT_ATTATCHMENTS')")
	public Response addAttatchment(@RequestParam("file") MultipartFile file) throws Exception {

		if (file != null) {
			if (!Arrays.asList(ConstantUtil.ALLOWED_DOCUMENT_FORMATS).contains(file.getContentType().split("/")[1])) {
				throw new BadRequestException("400",
						"File format not supported. Sent : " + file.getContentType().split("/")[1]);
			}
			
			if(file.getSize() > 3e+7)
				throw new BadRequestException("400",
						"File size exceeds 30MB ");
			try {
				
				String filePath = "";
				String fileName = "file" + "_Skilllink";
				fileName = fileName.trim().replaceAll(" ", "_");
				byte[] bytes = file.getBytes();

				Random rand = new Random();
				String randomNo = String.valueOf(System.currentTimeMillis())+"-"+rand.nextLong();
				fileName = fileName + randomNo + "." + getFileExtension(file.getOriginalFilename());//attachment.getDocumentBinaryType();
				Path path = Paths.get(filePath + fileName);

				Files.write(path, bytes);
				
				Response response = new Response();
				response.setDescription(fileName);
				return response;

			} catch (Exception ex) {
				throw ex;
			}

		} else {
			throw new BadRequestException("400", "File cannot be empty");
		}
	}
    
    private String getFileExtension(String name) {
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf+1);
	}
}
