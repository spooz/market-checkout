package me.balukiewicz.checkout.domain.item;

import lombok.Value;

@Value
class ItemQuantity {
    String id;
    Long quantity;
}