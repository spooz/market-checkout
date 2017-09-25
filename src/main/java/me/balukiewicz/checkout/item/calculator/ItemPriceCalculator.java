package me.balukiewicz.checkout.item.calculator;

import me.balukiewicz.checkout.item.dto.ItemFinalPrice;
import me.balukiewicz.checkout.item.dto.ItemQuantity;

import java.util.Set;

public interface ItemPriceCalculator {
    Set<ItemFinalPrice> calculateFinalPrice(Set<ItemQuantity> items);
    ItemFinalPrice calculateFinalPrice(ItemQuantity item);
}
