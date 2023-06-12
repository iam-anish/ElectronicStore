package com.lcwd.electronicstore2.repositories;

import com.lcwd.electronicstore2.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepositories extends JpaRepository<CartItem,Integer> {
}
