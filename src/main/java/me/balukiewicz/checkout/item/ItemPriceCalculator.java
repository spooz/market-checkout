package me.balukiewicz.checkout.item;

import me.balukiewicz.checkout.item.dto.ItemFinalPrice;
import me.balukiewicz.checkout.item.dto.ItemQuantity;

import java.util.Set;

interface ItemPriceCalculator {
    Set<ItemFinalPrice> calculateFinalPrice(Set<ItemQuantity> items);
    ItemFinalPrice calculateFinalPrice(ItemQuantity item);
}
