package me.balukiewicz.checkout.item.calculator;


import me.balukiewicz.checkout.item.domain.ItemPromotion;
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
        if(item.getPromotion()) {
            finalPrice = getFinalPrice(itemQuantity.getQuantity(), item.getPrice(), item.getPromotionUnit(), item.getPromotionPrice());
        } else {
            finalPrice = getFinalPrice(itemQuantity.getQuantity(), item.getPrice());
        }
        return new ItemFinalPrice(item.getId(), itemQuantity.getQuantity(), finalPrice);
    }

    private BigDecimal getFinalPrice(Long quantity, Double price, Long promUnit, Double promPirce) {
        return BigDecimal.valueOf((quantity / promUnit) * promPirce + (quantity / promUnit) * price);
    }

    private BigDecimal getFinalPrice(Long quantity, Double price) {
        return BigDecimal.valueOf(quantity * price);
    }

    public BigDecimal calculatePromotion(Set<String> itemsIds) {
        return BigDecimal.valueOf(
                itemPromotionRepository.findAll().stream()
                .filter(itemPromotion ->
                        itemsIds.contains(itemPromotion.getItem_id_first()) && itemsIds.contains(itemPromotion.getItem_id_second())
                )
                .mapToDouble(ItemPromotion::getDiscount).sum());
    }

    public BigDecimal calculatePromotionForItems(Set<ItemFinalPrice> items) {
        Set<String> itemIds = items.stream().map(ItemFinalPrice::getId).collect(Collectors.toSet());
        return calculatePromotion(itemIds);
    }




}
