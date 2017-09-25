package me.balukiewicz.checkout.item.calculator;


import me.balukiewicz.checkout.item.exception.ItemNotFoundException;
import me.balukiewicz.checkout.item.domain.Item;
import me.balukiewicz.checkout.item.domain.ItemPromotionRepository;
import me.balukiewicz.checkout.item.domain.ItemRepository;
import me.balukiewicz.checkout.item.dto.ItemFinalPrice;
import me.balukiewicz.checkout.item.dto.ItemQuantity;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemPriceCalculatorDefault implements ItemPriceCalculator {

    private final ItemRepository itemRepository;
    private final ItemPromotionRepository itemPromotionRepository;

    public ItemPriceCalculatorDefault(ItemRepository itemRepository, ItemPromotionRepository itemPromotionRepository) {
        this.itemRepository = itemRepository;
        this.itemPromotionRepository = itemPromotionRepository;
    }

    @Override
    public Set<ItemFinalPrice> calculateFinalPrice(Set<ItemQuantity> items) {
        return items.stream()
                .map(this::calculateFinalPrice)
                .collect(Collectors.toSet());
    }

    public ItemFinalPrice calculateFinalPrice(ItemQuantity itemQuantity) {

        Item item = itemRepository.findById(itemQuantity.getId())
                .orElseThrow(() -> new ItemNotFoundException("Item with id:" + itemQuantity.getId() + " not found"));

        BigDecimal finalPrice;
        if(item.getPromUnit() != null && item.getPromUnit() > 0) {
            finalPrice = BigDecimal.valueOf((itemQuantity.getQuantity() / item.getPromUnit()) * item.getPromPrice() +
                    itemQuantity.getQuantity() / item.getPromUnit() * item.getPrice());
        } else {
            finalPrice = BigDecimal.valueOf(itemQuantity.getQuantity() * item.getPrice());
        }

        return new ItemFinalPrice(item.getId(), itemQuantity.getQuantity(), finalPrice);
    }


}
