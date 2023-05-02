package com.lcwd.electronicstore2.exceptions;

import com.lcwd.electronicstore2.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //handling resource not found exception handler
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
          logger.info("Exception handler invoke !!");
          ApiResponseMessage responseMessage =  ApiResponseMessage.builder().message(ex.getMessage()).Status(HttpStatus.NOT_FOUND).success(true).build();
          return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
    }


    //MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
              List<ObjectError> allErrors =  ex.getBindingResult().getAllErrors();
              Map<String,Object> response = new HashMap<>();
              allErrors.stream().forEach(ObjectError ->{
                      String message = ObjectError.getDefaultMessage();
                      String feild = ((FieldError)ObjectError).getField();
                      response.put(message,feild);
              });
              return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }


    //Handle Bad Api Request
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequest ex){
        logger.info("Bad Api Reqiest !!");
        ApiResponseMessage responseMessage =  ApiResponseMessage.builder().message(ex.getMessage()).Status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);
    }
}
