package com.wallet.payment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateUserRequest {
	
	@NotBlank(message = "Name is required")
	private String name;
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid Email address")
	private String email;
	@NotBlank(message = "Mobile Number is required")
	@Pattern(regexp = "^[6-9][0-9]{9}$",message = "invalid Indian Mobile Number")
	private String mobile;
	
	public CreateUserRequest() {}

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
}
