package me.balukiewicz.checkout.domain.item;

import java.util.Set;

interface ItemPriceCalculator {
    Set<ItemFinalPrice> calculateFinalPrice(Set<ItemQuantity> items);
    ItemFinalPrice calculateFinalPrice(ItemQuantity item);
}
