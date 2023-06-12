package com.lcwd.electronicstore2.services.impl;

import com.lcwd.electronicstore2.dtos.AddItemToCart;
import com.lcwd.electronicstore2.dtos.CartDto;
import com.lcwd.electronicstore2.entities.Cart;
import com.lcwd.electronicstore2.entities.CartItem;
import com.lcwd.electronicstore2.entities.Product;
import com.lcwd.electronicstore2.entities.User;
import com.lcwd.electronicstore2.exceptions.BadApiRequest;
import com.lcwd.electronicstore2.exceptions.ResourceNotFoundException;
import com.lcwd.electronicstore2.repositories.CartItemRepositories;
import com.lcwd.electronicstore2.repositories.CartRepositories;
import com.lcwd.electronicstore2.repositories.ProductRepositories;
import com.lcwd.electronicstore2.repositories.UserRepositories;
import com.lcwd.electronicstore2.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepositories productRepositories;

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private CartRepositories cartRepositories;

    @Autowired
    private CartItemRepositories cartItemRepositories;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCart addItemToCart) {

        int quantity = addItemToCart.getQuantity();
        String productId = addItemToCart.getProductId();

        if (quantity<=0){
            throw new BadApiRequest("Requested quantity is not valid ");
        }
        //fetch the product
        Product product = productRepositories.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found !!"));
        //fetch the user from db
        User user = userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found !!"));

        Cart cart = null;
        try {
            cart = cartRepositories.findByUser(user).get();
        }catch (NoSuchElementException e){
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedDate(new Date());
        }

        //perform cart operation
        //if cart item already present; then update
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        items = items.stream().map(item ->{
              if (item.getProduct().getProductId().equals(productId)){
                  //item already present in cart
                  item.setQuantity(quantity);
                  item.setTotalPrice(quantity*product.getDiscountedPrice());
                  updated.set(true);
              }
              return item;
        }).collect(Collectors.toList());
//        cart.setItems(updatedItems);

        if (!updated.get()){
            //create item
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity*product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }


        cart.setUser(user);
        Cart updatedcart = cartRepositories.save(cart);
        return modelMapper.map(updatedcart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        CartItem cartItem1 = cartItemRepositories.findById(cartItem).orElseThrow(()->new ResourceNotFoundException("Cart Item not found !!"));
        cartItemRepositories.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        //fetch the user from db
        User user  = userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found !!"));
        Cart cart  = cartRepositories.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart not found !!"));
        cart.getItems().clear();

        cartRepositories.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user  = userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found !!"));
        Cart cart  = cartRepositories.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart not found !!"));
        return modelMapper.map(cart,CartDto.class);
    }
}
