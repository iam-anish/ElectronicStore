package com.lcwd.electronicstore2.services;

import com.lcwd.electronicstore2.dtos.OrderDto;
import com.lcwd.electronicstore2.dtos.PageableResponse;
import com.lcwd.electronicstore2.dtos.createOrderRequest;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(createOrderRequest orderDto);

    //remove order
    void removeOrder(String orderId);

    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);

    //get orders
    PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDir);
}
