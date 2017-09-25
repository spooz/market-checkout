package me.balukiewicz.checkout.item;

import lombok.AllArgsConstructor;
import me.balukiewicz.checkout.item.calculator.ItemPriceCalculator;
import me.balukiewicz.checkout.item.dto.CheckoutReport;
import me.balukiewicz.checkout.item.dto.ItemFinalPrice;
import me.balukiewicz.checkout.item.dto.ItemQuantity;
import me.balukiewicz.checkout.item.storage.ItemStorage;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
public class ItemFacade {

    private final ItemStorage itemStorage;
    private final ItemPriceCalculator itemPriceCalculator;

    public void store(ItemQuantity itemQuantity) {
        itemStorage.store(itemQuantity);
    }

    public CheckoutReport checkout() {
        Set<ItemFinalPrice> itemFinalPrices = itemPriceCalculator.calculateFinalItemPrices(itemStorage.getAll());
        BigDecimal promotion = itemPriceCalculator.calculatePromotionForItems(itemFinalPrices);
        BigDecimal finalPrice = itemPriceCalculator.calculateFinalPriceForItems(itemFinalPrices);
        CheckoutReport checkoutReport = new CheckoutReport(itemFinalPrices, promotion, finalPrice);
        itemStorage.clear();
        return checkoutReport;
    }


}
