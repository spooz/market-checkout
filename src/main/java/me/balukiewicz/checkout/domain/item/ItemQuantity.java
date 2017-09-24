package me.balukiewicz.checkout.domain.item;

import lombok.Value;

@Value
class ItemQuantity {
    String id;
    Long quantity;

    ItemQuantity(String id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }


}