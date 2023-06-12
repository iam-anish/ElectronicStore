package com.lcwd.electronicstore2.repositories;

import com.lcwd.electronicstore2.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String>{
}
