package com.lcwd.electronicstore2.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User{
    @Id
   private String userId;
    @Column(name = "user_name")
   private String name;
    @Column(name="user_email",unique = true)
   private String email;
    @Column(name = "user_password",length = 10)
   private String password;
   private String gender;
   @Column(length = 100)
   private String about;
   @Column(name = "user_image_name")
   private String imageName;

}
