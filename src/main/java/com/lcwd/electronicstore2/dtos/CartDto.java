package com.lcwd.electronicstore2.dtos;
import com.lcwd.electronicstore2.entities.User;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private String cartId;


    private Date createdDate;

    private User user;

    private List<CartItemDto> items = new ArrayList<>();
}
