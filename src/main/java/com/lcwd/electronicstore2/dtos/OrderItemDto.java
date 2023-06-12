package com.lcwd.electronicstore2.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItemDto {

    private int orderItemId;

    private int quantity;

    private int totalprice;

    private ProductDto product;

}
