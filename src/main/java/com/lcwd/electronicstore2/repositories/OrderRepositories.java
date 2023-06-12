package com.lcwd.electronicstore2.repositories;
import com.lcwd.electronicstore2.entities.Order;
import com.lcwd.electronicstore2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepositories extends JpaRepository<Order,String> {

    List<Order> findByUser(User user);
}
