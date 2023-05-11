package com.lcwd.electronicstore2.services.impl;

import com.lcwd.electronicstore2.dtos.PageableResponse;
import com.lcwd.electronicstore2.dtos.UserDto;
import com.lcwd.electronicstore2.entities.User;
import com.lcwd.electronicstore2.exceptions.ResourceNotFoundException;
import com.lcwd.electronicstore2.helper.Helper;
import com.lcwd.electronicstore2.repositories.UserRepositories;
import com.lcwd.electronicstore2.services.FileService;
import com.lcwd.electronicstore2.services.UserService;
import lombok.Builder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public UserDto createUser(UserDto userDto) {
        //genarate userId in String Format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //dto -> entity
        User user = dtoToEntity(userDto);
        User savedUser = userRepositories.save(user);
        //entity -> dto
        UserDto userDto1 = entityToDto(savedUser);

        return userDto1;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user = userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with this Id !!"));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());

        //save updated user
        User updatedUser = userRepositories.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with this Id !!"));

        //delete user profile image
        String fullPath = imagePath + user.getImageName();

        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            logger.info("User image not found in folder");
            ex.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //deleteUser
        userRepositories.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getALlUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("decs")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());


//        Page number defalut start from 0

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize,sort);
        Page<User> page = userRepositories.findAll(pageable);

        //Personal Method
//        List<User> users = page.getContent();
//        List<UserDto> userDtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
//
//        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
//        pageableResponse.setContent(userDtoList);
//        pageableResponse.setPageNumber(page.getNumber());
//        pageableResponse.setPageSize(page.getSize());
//        pageableResponse.setTotalPages(page.getTotalPages());
//        pageableResponse.setTotalElements(page.getTotalElements());
//        pageableResponse.setLastPage(page.isLast());


        //General Method so we can use it anywhere
        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page,UserDto.class);

        return pageableResponse;
    }
    @Override
    public UserDto getUserById(String userId) {
        User user = userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found!!"));

        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email){
        User user = userRepositories.findByEmail(email);

        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepositories.findByNameContaining(keyword);
        List<UserDto> userDtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public List<UserDto> getUserByIdAndName(String userId,String name){
        List<User> users = userRepositories.findByUserIdOrNameContaining(userId,name);
        List<UserDto> userDtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    private UserDto entityToDto(User savedUser) {
        //Manully Conversion
//          UserDto userDto = UserDto.builder()
//                  .userId(savedUser.getUserId())
//                  .name(savedUser.getName())
//                  .password(savedUser.getPassword())
//                  .gender(savedUser.getGender())
//                  .imageName(savedUser.getImageName())
//                  .email(savedUser.getEmail())
//                  .about(savedUser.getAbout()).build();
//
//          return userDto;

        //Using Mapper class
         return modelMapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
        //Manully Conversion
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .password(userDto.getPassword())
//                .imageName(userDto.getImageName()).build();
//
//        return user;

        //Using Mapper class
        return modelMapper.map(userDto,User.class);
    }
}
