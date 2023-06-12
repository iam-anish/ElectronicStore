package com.lcwd.electronicstore2.controllers;

import com.lcwd.electronicstore2.dtos.AddItemToCart;
import com.lcwd.electronicstore2.dtos.ApiResponseMessage;
import com.lcwd.electronicstore2.dtos.CartDto;
import com.lcwd.electronicstore2.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCart addItemToCart){
        CartDto cartDto = cartService.addItemToCart(userId,addItemToCart);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String  userId,@PathVariable int itemId){
        cartService.removeItemFromCart(userId,itemId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Item is removed !!")
                .success(true)
                .Status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){
        cartService.clearCart(userId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Cart is blank")
                .success(true)
                .Status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId){
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }
}
