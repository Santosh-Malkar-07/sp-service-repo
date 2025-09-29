package com.sp.security;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.sp.user.login.UserEntity;

public class SpUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private UserEntity userEntity;

	public SpUserDetails(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
	}

	@Override
	public String getPassword() {
		return userEntity.getUserPassword();
	}

	@Override
	public String getUsername() {
		return userEntity.getUserName();
	}

}
