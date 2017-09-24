package me.balukiewicz.checkout.item.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ItemFinalPrice {
    private String id;
    private Long quantity;
    private BigDecimal finalPrice;
}
