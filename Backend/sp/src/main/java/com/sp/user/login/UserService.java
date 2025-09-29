package com.sp.user.login;

import java.util.List;

public interface UserService {

	public UserEntity storeUser(UserDto user);

	public UserEntity getUser(String userId);

	public List<UserEntity> getUsers();

	public UserEntity updateUser(String userId, UserDto userEntity);

	public String deleteUser(String userId);

	public String verifyUserLoginOrNot(UserDto user);
}
