package com.lcwd.electronicstore2.repositories;

import com.lcwd.electronicstore2.dtos.UserDto;
import com.lcwd.electronicstore2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositories extends JpaRepository<User,String> {

       Optional<User> findByEmail(String email);
       //User findByEmailAndPassword(String email,String password);
       List<User> findByNameContaining(String keyword);

       List<User> findByUserIdOrNameContaining(String userId,String name);

}
