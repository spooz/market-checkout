package me.balukiewicz.checkout.item;

import lombok.AllArgsConstructor;
import me.balukiewicz.checkout.item.dto.CheckoutReport;
import me.balukiewicz.checkout.item.dto.ItemQuantity;

import java.math.BigDecimal;

@AllArgsConstructor
public class ItemFacade {

    private final ItemStorage itemStorage;
    private final ItemPriceCalculator itemPriceCalculator;

    public void store(ItemQuantity itemQuantity) {
        itemStorage.store(itemQuantity);
    }

    public CheckoutReport checkout() {
        CheckoutReport checkoutReport = new CheckoutReport(itemPriceCalculator.calculateFinalPrice(
                itemStorage.getAll()), BigDecimal.ZERO
        );
        itemStorage.clear();
        return checkoutReport;
    }


}
