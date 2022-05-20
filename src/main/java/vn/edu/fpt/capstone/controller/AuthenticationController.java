package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.SignInDto;
import vn.edu.fpt.capstone.dto.SignUpDto;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.service.AuthenticationService;
import vn.edu.fpt.capstone.service.UserService;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class.getName());
	@Autowired
	private AuthenticationService authenticationService;
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping({ "/hello" })
    public String firstPage() {
    	UserDetails u = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hello World" + u.getUsername() + " " + u.getAuthorities();
    }
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/signup")
	public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto) {
		ResponseObject response = new ResponseObject();       
		try {
			return authenticationService.signUpVerify(signUpDto);		
		} catch (Exception e) {
			logger.error(e.toString());
			response.setCode("500");
			response.setMessage("sign up request: " + e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody SignInDto signInDto) {
		ResponseObject response = new ResponseObject();
        try {
            return authenticationService.authenticate(signInDto);
        } catch (AuthenticationException e) {
            logger.error("Authenticate failed for username: " + signInDto.getUsername());
            logger.error(e.getMessage());
            
            response.setCode("401");
			response.setMessage("sign in request: username or pasword wrong!");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Undefined error");
            logger.error(e.getMessage());
            
            response.setCode("401");
			response.setMessage("sign in request: username or pasword wrong!");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
