package com.lcwd.electronicstore2.controllers;
import com.lcwd.electronicstore2.dtos.ApiResponseMessage;
import com.lcwd.electronicstore2.dtos.ImageResponse;
import com.lcwd.electronicstore2.dtos.PageableResponse;
import com.lcwd.electronicstore2.dtos.UserDto;
import com.lcwd.electronicstore2.entities.User;
import com.lcwd.electronicstore2.services.FileService;
import com.lcwd.electronicstore2.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

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
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber",defaultValue = "1",required = false) int pageNumber,
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

    @GetMapping("/getUserByIdAndName/")
    public List<UserDto> getUserByIdAndName(
            @RequestParam(value = "userId",defaultValue = "",required = false) String userId,
            @RequestParam(value = "name",defaultValue = "",required = false) String name){
        return userService.getUserByIdAndName(userId,name);
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


    //upload user Image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("userImage") MultipartFile image,
            @PathVariable String userId
            ) throws IOException {
       String imageName = fileService.uploadFile(image,imageUploadPath);

       //Saving image file in name in user table
       UserDto user = userService.getUserById(userId);
       user.setImageName(imageName);
       UserDto userDto = userService.updateUser(user,userId);

       ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).message("Image Uploaded Successfully").Status(HttpStatus.CREATED).build();

       return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    //serve Image
    @GetMapping("image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto userDto = userService.getUserById(userId);
        logger.info("User Image name: {}",userDto.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath,userDto.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
