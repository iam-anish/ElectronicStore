package com.lcwd.electronicstore2.dtos;

import com.lcwd.electronicstore2.validate.imageNameValidate;
import lombok.*;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto{
    private String userId;
    @Size(min = 3,max = 15,message = "Invalid Name !!")
    private String name;
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid user Email")
    @NotBlank(message = "Email is must required")
    private String email;
    @NotBlank(message = "Password is must required")
    private String password;
    @Size(min = 4,max = 6,message = "Invalid Gender")
    private String gender;
    @NotBlank(message = "Write something about yourself")
    private String about;

    @imageNameValidate
    private String imageName;
}
