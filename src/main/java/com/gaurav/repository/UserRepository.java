package com.gaurav.repository;



import com.gaurav.domain.UserStatus;
import com.gaurav.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);
    List<User> findByIsVerifiedTrue();

    List<User> findByIsVerifiedFalse();


}
