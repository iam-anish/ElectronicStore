package com.lcwd.electronicstore2.controllers;

import com.lcwd.electronicstore2.dtos.ApiResponseMessage;
import com.lcwd.electronicstore2.dtos.UserDto;
import com.lcwd.electronicstore2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") String userId,
           @Valid @RequestBody UserDto userDto){
         UserDto userDto1 =  userService.updateUser(userDto,userId);
         return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId ){
           userService.deleteUser(userId);
           ApiResponseMessage apiResponseMessage = ApiResponseMessage
                   .builder()
                   .message("User is deleted successfully")
                   .success(true)
                   .Status(HttpStatus.OK)
                   .build();
           return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    //get All
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        return new ResponseEntity<>(userService.getALlUsers(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    //get single
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    //search user
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
        return new ResponseEntity<>(userService.searchUser(keyword),HttpStatus.OK);
    }
}
