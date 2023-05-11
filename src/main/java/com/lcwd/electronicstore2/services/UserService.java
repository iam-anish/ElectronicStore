package com.lcwd.electronicstore2.services;

import com.lcwd.electronicstore2.dtos.PageableResponse;
import com.lcwd.electronicstore2.dtos.UserDto;

import java.util.List;

public interface UserService{
    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId);

    //delete
    void deleteUser(String userId);

    //getAllUser
    PageableResponse<UserDto> getALlUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    //getSingleUser
    UserDto getUserById(String userId);

    //getSingleUserByEmail
    UserDto getUserByEmail(String email);

    //searchUser
    List<UserDto> searchUser(String keyword);

    List<UserDto> getUserByIdAndName(String userId,String name);
}
