package com.sp.user.login;

import java.math.BigInteger;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	@NotBlank(message = "User ID cannot be empty")
	@NotNull(message = "User ID cannot be Null")
	@Size(min = 1, max = 20, message = "User ID must be between 1 and 20 characters")
	@Pattern(regexp = "[0-9A-Za-z\\_-]*", message = "User Id contain only alphanumeric characters and underscores")
	String userId;

	@NotNull(message = "User Name cannot be Null")
	@NotBlank(message = "User name is required")
	@Size(min = 6, max = 30, message = "Username must be between 6 and 30 characters")
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "Username can only contain alphanumeric characters and underscores, and must start with an alphabetic character")
	private String userName;

	@NotBlank(message = "First Name Should not Blank")
	@NotNull(message = "First Name cannot be Null")
	@Pattern(regexp = "[A-Za-z]*", message = "First Name can only contain characters")
	@Size(min = 2, max = 30, message = "First Name must be between 2 and 30 characters")
	String firstName;

	String middleName;

	@NotNull(message = "Last Name cannot be Null")
	@NotBlank(message = "Last Name Should not Blank")
	@Pattern(regexp = "[A-Za-z]*", message = "Last Name can only contain characters")
	@Size(min = 2, max = 30, message = "Last Name must be between 2 and 30 characters")
	String lastName;

	@NotNull(message = "Password cannot be Null")
	@NotBlank(message = "Password cannot be empty")
	@Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,30}$", message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character, and no whitespaces")
	String userPassword;

	@NotNull(message = "Mobile cannot be Null")
	@NotBlank(message = "Mobile Number Should not Blank")
	@Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid mobile number format")
	String mobile;

	@Email
	@NotNull(message = "Email cannot be Null")
	@NotBlank(message = "Email Should not Blank")
	String email;

	String address;

}
