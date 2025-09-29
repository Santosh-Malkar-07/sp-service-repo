package com.sp.security;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sp.user.login.UserEntity;
import com.sp.user.login.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = Optional.ofNullable(userRepo.findByUserName(username));
		if (user.isPresent()) {
			return new SpUserDetails(user.get());
		} else {
			throw new UsernameNotFoundException("User Not Found");
		}
	}
}
