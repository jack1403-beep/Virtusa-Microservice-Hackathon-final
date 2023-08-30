package com.example.authservice.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.authservice.model.User;




@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
	User findByEmail(String email);

}
