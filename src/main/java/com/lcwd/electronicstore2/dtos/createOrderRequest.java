package com.lcwd.electronicstore2.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class createOrderRequest {

    @NotBlank(message = "userId is required")
    private String userId;
    @NotBlank(message = "CartId is required")
    private String cartId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    @NotBlank(message = "Address is required")
    private String billingAddress;
    @NotBlank(message = "Phone is required")
    private String billingPhone;
    @NotBlank(message = "Billing Name is required")
    private String billingName;

}
