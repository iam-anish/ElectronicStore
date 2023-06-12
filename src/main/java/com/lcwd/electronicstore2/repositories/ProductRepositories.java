package com.lcwd.electronicstore2.repositories;

import com.lcwd.electronicstore2.dtos.ProductDto;
import com.lcwd.electronicstore2.entities.Category;
import com.lcwd.electronicstore2.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepositories extends JpaRepository<Product,String> {

    //search
    Page<Product> findByTitleContaining(String subTitle,Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByCategory(Category category,Pageable pageable);
}
