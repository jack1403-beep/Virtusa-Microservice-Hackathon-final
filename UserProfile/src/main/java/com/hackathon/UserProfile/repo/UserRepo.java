package com.hackathon.UserProfile.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.UserProfile.model.User;


@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
	User findByEmail(String email);

}
