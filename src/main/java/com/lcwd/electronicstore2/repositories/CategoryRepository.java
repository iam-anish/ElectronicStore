package com.lcwd.electronicstore2.repositories;

import com.lcwd.electronicstore2.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String>{

    @Query(value = "SELECT * FROM categories WHERE CONCAT_WS(' ', category_title, category_desc) like %:query%", nativeQuery = true)
    List<Category> findBySomeFieldContaining(@Param("query") String query);

}
