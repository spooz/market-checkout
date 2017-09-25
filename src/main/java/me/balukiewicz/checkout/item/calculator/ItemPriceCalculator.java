package me.balukiewicz.checkout.item.calculator;

import me.balukiewicz.checkout.item.dto.ItemFinalPrice;
import me.balukiewicz.checkout.item.dto.ItemQuantity;

import java.math.BigDecimal;
import java.util.Set;

public interface ItemPriceCalculator {
    Set<ItemFinalPrice> calculateFinalItemPrices(Set<ItemQuantity> items);
    ItemFinalPrice calculateFinalItemPrice(ItemQuantity item);

    BigDecimal calculateFinalPriceForItems(Set<ItemFinalPrice> items);

    BigDecimal calculatePromotionForItems(Set<ItemFinalPrice> items);
}
