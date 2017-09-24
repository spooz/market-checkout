package me.balukiewicz.checkout.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ItemConfig {

    @Bean
    ItemStorage itemStorage(ItemRepository itemRepository) {
        return new ItemStorageInMemory(itemRepository);
    }

    @Bean
    ItemPriceCalculator itemPriceCalculator(ItemRepository itemRepository, ItemPromotionRepository itemPromotionRepository) {
        return new ItemPriceCalculatorDefault(itemRepository, itemPromotionRepository);
    }

    @Bean
    ItemFacade itemFacade(ItemStorage itemStorage, ItemPriceCalculator itemPriceCalculator) {
        return new ItemFacade(itemStorage, itemPriceCalculator);
    }

}
