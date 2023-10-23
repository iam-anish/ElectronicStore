package com.lcwd.electronicstore2.services;

import com.lcwd.electronicstore2.dtos.UserDto;
import com.lcwd.electronicstore2.entities.Role;
import com.lcwd.electronicstore2.entities.User;
import com.lcwd.electronicstore2.repositories.RoleRepositories;
import com.lcwd.electronicstore2.repositories.UserRepositories;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class  UserServiceTest {

    @MockBean
    private RoleRepositories roleRepositories;

    @MockBean
    private UserRepositories userRepositories;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    User user;
    Role role;

    String roleId;

    @BeforeEach
    public void init(){

        role = Role.builder()
                .roleId("abc")
                .roleName("NORMAL")
                .build();

        user = User.builder()
                .name("Anish")
                .email("anish@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("abc.png")
                .password("LCWD")
                .roles(Set.of(role))
                .build();

        roleId = "abc";
    }

    @Test
    public void createUserTest(){
        Mockito.when(userRepositories.save(Mockito.any())).thenReturn(user);
        Mockito.when(roleRepositories.findById(Mockito.anyString())).thenReturn(Optional.of(role));

        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Anish",user1.getName());
    }

    @Test
    public void updateUserTest(){
        String userId = "dsvsdadv";
        UserDto userDto = UserDto.builder()
                .name("Anish Mathakiya")
                .about("This is updated user details")
                .gender("Male")
                .imageName("xyz.png")
                .build();

        Mockito.when(userRepositories.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepositories.save(Mockito.any())).thenReturn(user);

        UserDto updatedUser = userService.updateUser(userDto,userId);
        System.out.println(updatedUser.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(),updatedUser.getName(),"Name is not valid");
    }
}
