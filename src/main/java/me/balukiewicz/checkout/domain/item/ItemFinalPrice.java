package me.balukiewicz.checkout.domain.item;

import lombok.Value;

import java.math.BigDecimal;

@Value
class ItemFinalPrice {
    String id;
    Long quantity;
    BigDecimal finalPrice;

    ItemFinalPrice(String id, Long quantity, BigDecimal finalPrice) {
        this.id = id;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
    }
}
