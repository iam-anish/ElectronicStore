package com.lcwd.electronicstore2.exceptions;


import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
       super("Resource not found Exception");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
