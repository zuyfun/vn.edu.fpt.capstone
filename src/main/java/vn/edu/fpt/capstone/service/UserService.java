package vn.edu.fpt.capstone.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.SignUpDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.UserModel;

@Service
public interface UserService {
	UserModel createUser(SignUpDto signUpDto);
	UserModel updateUser(UserDto userDto);
	boolean deleteUserById(Long id);
	boolean checkIdExist(Long id);
	List<UserDto> getAllUser();
	boolean existsByUsername(String username);
	ResponseEntity<?> getUserInformationById(Long id, String jwtToken);
	ResponseEntity<?> getUserInformationByToken(String jwtToken);
	UserModel findByVerificationCode(String code);
}
