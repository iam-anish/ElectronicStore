package com.lcwd.electronicstore2.repositories;

import com.lcwd.electronicstore2.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepositories extends JpaRepository<OrderItem,Integer> {
}
