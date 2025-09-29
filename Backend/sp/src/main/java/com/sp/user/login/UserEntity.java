package com.sp.user.login;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_info")
public class UserEntity {

	@Id
	private String userId;

	private String userName;

	private String firstName;

	private String middleName;

	private String lastName;

	private String userPassword;

	private String mobile;

	private String email;

	private String address;
}
