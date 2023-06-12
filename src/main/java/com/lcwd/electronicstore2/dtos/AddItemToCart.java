package com.lcwd.electronicstore2.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemToCart {

    private String productId;
    private int quantity;
}
