package com.lcwd.electronicstore2.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtRequest{
    private String email;
    private String password;
}
