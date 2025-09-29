package com.sp.user.login;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.CorsFilter;
import com.sp.exception.handlers.BusinessException;
import com.sp.jwt.JwtService;
import com.sp.user.details.User;

@Service
public class UserImpI implements UserService {

    private final CorsFilter corsFilter;

	@Autowired
	UserRepo userRepo;

	@Autowired
	ModelMapper mapper;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    UserImpI(CorsFilter corsFilter) {
        this.corsFilter = corsFilter;
    }

	@Override
	public UserEntity storeUser(UserDto user) {

		if (Objects.nonNull(user)) {
			Optional<UserEntity> existingUser = userRepo.findById(user.getUserId());
			if (existingUser.isPresent()) {
				throw new BusinessException("User Id Already Exit");
			} else {
				Optional<UserEntity> userEntity = Optional.ofNullable(userRepo.findByUserName(user.getUserName()));
				if (userEntity.isPresent()) {
					throw new BusinessException("User Name Already Exit");
				}
			}

			UserEntity userEntity = mapper.map(user, UserEntity.class);
			userEntity.setUserPassword(bCryptPasswordEncoder.encode(userEntity.getUserPassword()));
			return userRepo.save(userEntity);
		} else {
			throw new BusinessException("User Dto should not be null");
		}
	}

	@Override
	public UserEntity getUser(String userId) {

		Optional<UserEntity> user = userRepo.findById(userId);

		if (user.isPresent()) {
			return user.get();
		} else {
			throw new BusinessException("User not found for given id: " + userId);
		}
	}

	@Override
	public List<UserEntity> getUsers() {
		Optional<List<UserEntity>> userList = Optional.ofNullable(userRepo.findAll());
		if (userList.isPresent()) {
			return userList.get();
		} else {
			throw new BusinessException("No Record Found");
		}

	}

	@Override
	public UserEntity updateUser(String userId, UserDto userDto) {

		UserEntity userEntity = mapper.map(userDto, UserEntity.class);
		Optional<UserEntity> user = userRepo.findById(userId);
		if (user.isPresent()) {
			Class<?> userFiled = UserEntity.class;
			Field[] fields = userFiled.getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);
				try {
					Object value = field.get(userEntity);
					if (Objects.nonNull(value)) {
						field.set(user.get(), value);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return userRepo.save(user.get());

		} else {
			throw new BusinessException("User not found for id: " + userId + " " + "to Update the record");
		}
	}

	@Override
	public String deleteUser(String userId) {
		if (Objects.nonNull(userId)) {
			userRepo.deleteById(userId);
			return "User Deleted Successuflly";
		} else {
			throw new BusinessException("User Id should not be null: " + userId);
		}
	}

	@Override
	public String verifyUserLoginOrNot(UserDto userDto) {

		UserEntity userEntity = mapper.map(userDto, UserEntity.class);
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userEntity.getUserName(), userEntity.getUserPassword()));
			if (authentication.isAuthenticated()) {
				return jwtService.generateToken(userDto.getUserName());
			}else {
				throw new BusinessException("Invalid Credentials");
			}
		} catch (Exception e) {
			throw new BusinessException("Invalid Credentials");
		}
		
	}
}
