package com.sp.user.details;

import java.util.List;

public interface UserInterface {

	public User storeUser(User user) ;
	
	public User getUser(String id);
	
	public List<User> getAllUsers();
	
	public User updateUser(String id, User user);
	
	public String deleteUser(String id);
}
