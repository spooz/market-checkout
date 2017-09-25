package me.balukiewicz.checkout.item.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Set;

@Value
public class CheckoutReport {
    private Set<ItemFinalPrice> itemFinalPrices;
    private BigDecimal promotion;
    private BigDecimal finalPrice;
}
