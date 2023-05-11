package com.lcwd.electronicstore2.services;

import com.lcwd.electronicstore2.dtos.CategoryDto;
import com.lcwd.electronicstore2.dtos.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto,String categoryId);

    //delete
    void delete(String categoryId);

    //get All
    PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);

    //get single category details
    CategoryDto get(String categoryId);

    //search


}
