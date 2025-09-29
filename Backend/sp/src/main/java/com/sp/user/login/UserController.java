package com.sp.user.login;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/userApi")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/storeUser")
	public ResponseEntity<UserEntity> storeUser(@RequestBody @Valid UserDto user) {
		UserEntity userResult = userService.storeUser(user);
		return new ResponseEntity<>(userResult, HttpStatus.CREATED);
	}

	@GetMapping("/getUser/{userId}")
	public ResponseEntity<UserEntity> getUser(@PathVariable String userId) {
		UserEntity userEntity = userService.getUser(userId);
		return new ResponseEntity<>(userEntity, HttpStatus.OK);
	}

	@GetMapping("/getUsers")
	public ResponseEntity<List<UserEntity>> getAllUser() {
		List<UserEntity> userList = userService.getUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@PatchMapping("/updateUser/{userId}")
	public ResponseEntity<UserEntity> updateUser(@PathVariable String userId, @RequestBody UserDto userEntity) {
		UserEntity userEntity2 = userService.updateUser(userId, userEntity);
		return new ResponseEntity<>(userEntity2, HttpStatus.OK);
	}

	@DeleteMapping("deleteUser/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable String userId) {
		String status = userService.deleteUser(userId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@PostMapping("/login")
	public String loginUser(@RequestBody UserDto user) {
		String s = userService.verifyUserLoginOrNot(user);
		return s;
	}
}
