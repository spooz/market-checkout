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
    public Set<ItemFinalPrice> calculateFinalItemPrices(Set<ItemQuantity> items) {
        return items.stream()
                .map(this::calculateFinalItemPrice)
                .collect(Collectors.toSet());
    }

    @Override
    public ItemFinalPrice calculateFinalItemPrice(ItemQuantity itemQuantity) {
        Item item = itemRepository.findById(itemQuantity.getId())
                .orElseThrow(() -> new ItemNotFoundException("Item with id:" + itemQuantity.getId() + " not found"));

        BigDecimal finalPrice;
        if(item.getHasPromotion()) {
            finalPrice = getFinalPrice(itemQuantity.getQuantity(), item.getPrice(), item.getPromotionUnit(), item.getPromotionPrice());
        } else {
            finalPrice = getFinalPrice(itemQuantity.getQuantity(), item.getPrice());
        }
        return new ItemFinalPrice(item.getId(), itemQuantity.getQuantity(), finalPrice);
    }

    private BigDecimal getFinalPrice(Long quantity, BigDecimal price, Long promUnit, BigDecimal promPirce) {
        return promPirce.multiply(BigDecimal.valueOf(quantity / promUnit)).add(price.multiply(BigDecimal.valueOf(quantity % promUnit)));
    }

    private BigDecimal getFinalPrice(Long quantity, BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public BigDecimal calculateFinalPriceForItems(Set<ItemFinalPrice> items) {
        return items.stream()
                .map(ItemFinalPrice::getFinalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public BigDecimal calculatePromotionForItems(Set<ItemFinalPrice> items) {
        Set<String> itemsIds = items.stream().map(ItemFinalPrice::getId).collect(Collectors.toSet());
        return itemPromotionRepository.findAll().stream()
                        .filter(itemPromotion ->
                                itemsIds.contains(itemPromotion.getItemIdFirst()) && itemsIds.contains(itemPromotion.getItemIdSecond())
                        )
                        .map(ItemPromotion::getDiscount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }




}
