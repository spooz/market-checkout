package me.balukiewicz.checkout.item

import me.balukiewicz.checkout.item.calculator.ItemPriceCalculatorDefault
import me.balukiewicz.checkout.item.domain.Item
import me.balukiewicz.checkout.item.domain.ItemPromotionRepository
import me.balukiewicz.checkout.item.domain.ItemRepository
import me.balukiewicz.checkout.item.dto.ItemQuantity
import me.balukiewicz.checkout.item.exception.ItemNotFoundException
import spock.lang.Specification

class ItemPriceCalculatorDefaultSpec extends Specification{

    def itemPriceCalculator
    def ItemRepository itemRepository
    def ItemPromotionRepository itemPromotionRepository

    def setup() {
        itemRepository = Stub(ItemRepository.class)
        itemPromotionRepository = Stub(ItemPromotionRepository.class)
        itemPriceCalculator = new ItemPriceCalculatorDefault(itemRepository, itemPromotionRepository)
    }

    def "should calculate price for item without quantity promotion"() {
        given:
            def item1 = new Item("id1", 2, 0, 0)
            def itemQuantity1 = new ItemQuantity("id1", 5L)
            itemRepository.findById("id1") >> Optional.of(item1)

        when:
            def finalPrice = itemPriceCalculator.calculateFinalPrice(itemQuantity1)


        then:
            finalPrice.id == "id1"
            finalPrice.quantity == 5L
            finalPrice.finalPrice == BigDecimal.valueOf(10)
    }

    def "should calculate price for item with quantity promotion"() {
        given:
            def item1 = new Item("id1", 2, 4, 1)
            def itemQuantity1 = new ItemQuantity("id1", 10L)
            itemRepository.findById("id1") >> Optional.of(item1)

        when:
            def finalPrice = itemPriceCalculator.calculateFinalPrice(itemQuantity1)


        then:
            finalPrice.id == "id1"
            finalPrice.quantity == 10L
            finalPrice.finalPrice == BigDecimal.valueOf(6)
    }

    def "should throw ItemNotFoundException when item quantity is with wrong item id"() {
        given:
            def itemQuantity1 = new ItemQuantity("id1", 10L)
            itemRepository.findById("id1") >> Optional.empty()

            when:
                def finalPrice = itemPriceCalculator.calculateFinalPrice(itemQuantity1)

            then:
                thrown ItemNotFoundException
    }

}
