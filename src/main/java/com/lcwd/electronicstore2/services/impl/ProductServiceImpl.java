package com.lcwd.electronicstore2.services.impl;

import com.lcwd.electronicstore2.dtos.CategoryDto;
import com.lcwd.electronicstore2.dtos.PageableResponse;
import com.lcwd.electronicstore2.dtos.ProductDto;
import com.lcwd.electronicstore2.entities.Category;
import com.lcwd.electronicstore2.entities.Product;
import com.lcwd.electronicstore2.exceptions.ResourceNotFoundException;
import com.lcwd.electronicstore2.helper.Helper;
import com.lcwd.electronicstore2.repositories.CategoryRepository;
import com.lcwd.electronicstore2.repositories.ProductRepositories;
import com.lcwd.electronicstore2.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepositories productRepositories;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${product.image.path}")
    private String imagePath;

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = modelMapper.map(productDto,Product.class);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(new Date());
        Product savedProduct =  productRepositories.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepositories.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found for this id"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setAddedDate(productDto.getAddedDate());
        product.setStock(productDto.isStock());
        product.setLive(productDto.isLive());
        product.setProductImageName(productDto.getProductImageName());
        Product updatedProduct = productRepositories.save(product);
        return modelMapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepositories.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found for this id"));
        //delete user profile image
        String fullPath = imagePath + product.getProductImageName();
        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            ex.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        productRepositories.delete(product);
    }

    @Override
    public ProductDto get(String productId){
        Product product = productRepositories.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found for this id"));
        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepositories.findAll(pageable);

        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepositories.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepositories.findByTitleContaining(subTitle,pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found !!"));
        Product product = modelMapper.map(productDto,Product.class);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product savedProduct =  productRepositories.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto updateCategory(String productId, String caregoryId) {

        //Product fetch
        Product product = productRepositories.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found !!"));
        //category fetch
        Category category = categoryRepository.findById(caregoryId).orElseThrow(()->new ResourceNotFoundException("Category not found !!"));
        product.setCategory(category);
        Product product1 = productRepositories.save(product);

        return modelMapper.map(product1,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found !!"));
        Page<Product> page = productRepositories.findByCategory(category,pageable);

        return Helper.getPageableResponse(page,ProductDto.class);
    }


}
