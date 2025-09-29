package com.sp.user.details;

import java.util.List;
import java.util.Optional;

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

import com.sp.exception.handlers.BusinessException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserCont {

	@GetMapping("/api/data")
	public List<String> getAllData() {
		return List.of("Item 1", "Item 2", "Item 3");
	}

	@Autowired
	private UserInterface userInterface;

	@PostMapping("/storeUser")
	public ResponseEntity<User> storeUser(@RequestBody User user) {
		User user2 = userInterface.storeUser(user);
		return new ResponseEntity<>(user2, HttpStatus.CREATED);
	}

	@GetMapping("/getUser/{id}")
	public ResponseEntity<User> getUser(@PathVariable String id) {
		return new ResponseEntity<>(userInterface.getUser(id), HttpStatus.OK);
	}

	@GetMapping("/getUsers")
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> userList = userInterface.getAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@PatchMapping("/updateUser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
		return new ResponseEntity<>(userInterface.updateUser(id, user), HttpStatus.OK);
	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable String id) {
		Optional<String> result = Optional.ofNullable(userInterface.deleteUser(id));
		if (result.isPresent()) {
			return ResponseEntity.noContent().build();
		} else {
			throw new BusinessException("Record deletion fails");
		}

	}

}
