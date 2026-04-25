package com.gaurav.service;


import com.gaurav.domain.UserStatus;
import com.gaurav.domain.VerificationType;
import com.gaurav.exception.UserException;
import com.gaurav.model.User;

import java.util.List;


public interface UserService {

	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findUserByEmail(String email) throws UserException;
	
	public User findUserById(Long userId) throws UserException;

	public User verifyUser(User user) throws UserException;

	public User enabledTwoFactorAuthentication(VerificationType verificationType,
											   String sendTo, User user) throws UserException;

//	public List<User> getPenddingRestaurantOwner();

	User updatePassword(User user, String newPassword);

	void sendUpdatePasswordOtp(String email,String otp);

//	void sendPasswordResetEmail(User user);

    // 🔹 Admin: get all users
    List<User> getAllUsers();

    // 🔹 Admin: get verified users
    List<User> getVerifiedUsers();

    // 🔹 Admin: get non-verified users
    List<User> getNonVerifiedUsers();

    // 🔹 Optional but useful
}
