package com.lcwd.electronicstore2.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseMessage{
    private String message;
    private boolean success;
    private HttpStatus Status;
}
