package com.lcwd.electronicstore2.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtResponse{
    private String jwtToken;
    private UserDto user;
}
