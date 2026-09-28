package com.example.usermanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserRequestDto {

	@NotNull(message = "Name is Required")
	private String name;
	@Email(message = "Invalid Email")
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRequestDto(@NotNull(message = "Name is Required") String name,
			@Email(message = "Invalid Email") String email) {
		this.name = name;
		this.email = email;
	}

}
