package com.lcwd.electronicstore2.services.impl;

import com.lcwd.electronicstore2.dtos.OrderDto;
import com.lcwd.electronicstore2.dtos.PageableResponse;
import com.lcwd.electronicstore2.dtos.createOrderRequest;
import com.lcwd.electronicstore2.entities.*;
import com.lcwd.electronicstore2.exceptions.BadApiRequest;
import com.lcwd.electronicstore2.exceptions.ResourceNotFoundException;
import com.lcwd.electronicstore2.helper.Helper;
import com.lcwd.electronicstore2.repositories.CartRepositories;
import com.lcwd.electronicstore2.repositories.OrderRepositories;
import com.lcwd.electronicstore2.repositories.UserRepositories;
import com.lcwd.electronicstore2.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private OrderRepositories orderRepositories;

    @Autowired
    private CartRepositories cartRepositories;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(createOrderRequest orderDto) {
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();

        User user = userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found !!"));

        Cart cart = cartRepositories.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart not found"));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.size()==0){
            throw new BadApiRequest("Invalid number item in cart");
        }

        Order order = Order.builder()
                .orderId(UUID.randomUUID().toString())
                .billingAddress(orderDto.getBillingAddress())
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .orderStatus(orderDto.getOrderStatus())
                .paymentStatus(orderDto.getPaymentStatus())
                .orderedDate(new Date())
                .user(user)
                .build();

        //orderAmount,orderItem
        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalprice(cartItem.getQuantity()*cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get()+orderItem.getTotalprice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepositories.save(cart);
        Order savedOrder = orderRepositories.save(order);
        return modelMapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepositories.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not found !!"));
        orderRepositories.delete(order);
    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        User user = userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found !!"));
        List<Order> orders = orderRepositories.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderRepositories.findAll(pageable);
        return Helper.getPageableResponse(page, OrderDto.class);
    }
}
