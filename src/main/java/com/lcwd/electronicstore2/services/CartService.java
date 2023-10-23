package com.lcwd.electronicstore2.services;

import com.lcwd.electronicstore2.dtos.AddItemToCart;
import com.lcwd.electronicstore2.dtos.CartDto;
import com.lcwd.electronicstore2.entities.Cart;

public interface CartService {

    //add item
    CartDto addItemToCart(String userId, AddItemToCart addItemToCart);

    //remove item form cart
    void removeItemFromCart(String userId,int cartItem);

    //clear cart
    void clearCart(String userId);

    Cart getCartByUser(String userId);
}
