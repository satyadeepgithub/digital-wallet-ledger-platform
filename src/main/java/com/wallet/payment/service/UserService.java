package com.wallet.payment.service;

import com.wallet.payment.dto.CreateUserRequest;
import com.wallet.payment.dto.UserResponse;


public interface UserService {
	
	UserResponse createUser(CreateUserRequest request);
	
	UserResponse getUserById(Long Id);

}
