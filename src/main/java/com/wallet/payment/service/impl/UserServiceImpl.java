package com.wallet.payment.service.impl;

import org.springframework.stereotype.Service;

import com.wallet.payment.dto.CreateUserRequest;
import com.wallet.payment.dto.UserResponse;
import com.wallet.payment.entity.User;
import com.wallet.payment.exception.DuplicateUserException;
import com.wallet.payment.exception.UserNotFoundException;
import com.wallet.payment.repository.UserRepository;
import com.wallet.payment.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserResponse createUser(CreateUserRequest request) {
		
		String email = request.getEmail().toLowerCase().trim();
		String mobile = request.getMobile().trim();
		
		if(userRepository.existsByEmail(email)) {
			throw new DuplicateUserException("Email already registered "+email);
		}
		
		if(userRepository.existsByMobile(mobile)) {
			throw new DuplicateUserException("Mobile Number already registered "+mobile);
		}
			
		User user = new User(request.getName(),email,mobile);
		User savedUser = userRepository.save(user);
		return mapToResponse(savedUser);
	}

	@Override
	public UserResponse getUserById(Long id) {
		 User user = userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException(id));
		 return mapToResponse(user);
	}
	
	 private UserResponse mapToResponse(User user) {

	        return new UserResponse(
	                user.getId(),
	                user.getName(),
	                user.getEmail(),
	                user.getMobile(),
	                user.getCreatedAt(),
	                user.getUpdatedAt()
	        );
	    }

}
