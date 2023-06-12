package com.lcwd.electronicstore2.repositories;

import com.lcwd.electronicstore2.entities.Cart;
import com.lcwd.electronicstore2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepositories extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);
}
