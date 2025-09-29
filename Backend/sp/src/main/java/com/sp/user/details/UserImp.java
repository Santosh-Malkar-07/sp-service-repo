package com.sp.user.details;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.exception.handlers.BusinessException;

@Service
public class UserImp implements UserInterface {

	@Autowired
	UserRep userRepo;

	@Override
	public User storeUser(User user) {
		try {
			return userRepo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUser(String id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new BusinessException("User not found for id: " + id);
		}
	}

	public List<User> getAllUsers() {
		Optional<List<User>> optional = Optional.ofNullable(userRepo.findAll());
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new BusinessException("No Record Present in DB");
		}
	}

	@Override
	public User updateUser(String id, User user) {
		Optional<User> exitingUser = userRepo.findById(id);
		if (exitingUser.isPresent()) {
			Class<?> userFields = User.class;
			Field[] field = userFields.getDeclaredFields();

			for (Field field2 : field) {
				field2.setAccessible(true);
				try {
					Object value = field2.get(user);
					if (Objects.nonNull(value)) {
						field2.set(exitingUser.get(), value);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				field2.setAccessible(false);
			}
			return userRepo.save(exitingUser.get());
		} else {
			throw new BusinessException("User not found for id: " + id);
		}
	}

	@Override
	public String deleteUser(String id) {

		if (Objects.nonNull(id)) {
			userRepo.deleteById(id);
			return "User Deleted Successful for Id: " + id;
		} else {
			throw new BusinessException("User id should not be null");
		}

	}

}
