package me.balukiewicz.checkout.item

import me.balukiewicz.checkout.item.calculator.ItemPriceCalculatorDefault
import me.balukiewicz.checkout.item.domain.Item
import me.balukiewicz.checkout.item.domain.ItemPromotion
import me.balukiewicz.checkout.item.domain.ItemPromotionRepository
import me.balukiewicz.checkout.item.domain.ItemRepository
import me.balukiewicz.checkout.item.dto.ItemFinalPrice
import me.balukiewicz.checkout.item.dto.ItemQuantity
import me.balukiewicz.checkout.item.exception.ItemNotFoundException
import org.assertj.core.util.Lists
import org.assertj.core.util.Sets
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
            def item1 = new Item("id1", BigDecimal.valueOf(2), false, 0, BigDecimal.ZERO)
            def itemQuantity1 = new ItemQuantity("id1", 5L)
            itemRepository.findById("id1") >> Optional.of(item1)

        when:
            def finalPrice = itemPriceCalculator.calculateFinalItemPrice(itemQuantity1)


        then:
            finalPrice.id == "id1"
            finalPrice.quantity == 5L
            finalPrice.finalPrice == BigDecimal.valueOf(10)
    }

    def "should calculate price for item with quantity promotion"() {
        given:
            def item1 = new Item("id1", BigDecimal.valueOf(2), true, 4, BigDecimal.ONE)
            def itemQuantity1 = new ItemQuantity("id1", 10L)
            itemRepository.findById("id1") >> Optional.of(item1)

        when:
            def finalPrice = itemPriceCalculator.calculateFinalItemPrice(itemQuantity1)


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
                def finalPrice = itemPriceCalculator.calculateFinalItemPrice(itemQuantity1)

            then:
                thrown ItemNotFoundException
    }

    def "should calculate sum price for give items final prices"() {
        given:
            def itemFinalPrice1 = new ItemFinalPrice("id1", 3, BigDecimal.valueOf(10))
            def itemFinalPrice2 = new ItemFinalPrice("id2", 5, BigDecimal.valueOf(10))
            def itemFinalPrice3 = new ItemFinalPrice("id3", 5, BigDecimal.valueOf(10))

            def items = Sets.newLinkedHashSet(itemFinalPrice1, itemFinalPrice2, itemFinalPrice3)

        when:
            def sum = itemPriceCalculator.calculateSumPriceForItems(items)

        then:
            sum == BigDecimal.valueOf(30)

    }

    def "should calculate promotion for given items final prices and set of promotions"() {
        given:
            def itemFinalPrice1 = new ItemFinalPrice("id1", 3, BigDecimal.valueOf(10))
            def itemFinalPrice2 = new ItemFinalPrice("id2", 5, BigDecimal.valueOf(10))
            def itemFinalPrice3 = new ItemFinalPrice("id3", 5, BigDecimal.valueOf(10))

            def items = Sets.newLinkedHashSet(itemFinalPrice1, itemFinalPrice2, itemFinalPrice3)

            def itemPromotion1 = new  ItemPromotion("id1", "id2", BigDecimal.valueOf(10))
            def itemPromotion2 = new  ItemPromotion("id1", "id3", BigDecimal.valueOf(10))

            itemPromotionRepository.findAll() >> Lists.newArrayList(itemPromotion1, itemPromotion2)

        when:
            def promotion = itemPriceCalculator.calculatePromotionForItems(items)

        then:
            promotion ==  BigDecimal.valueOf(20)
    }

    def "should calculate final price with given sum and promotion"() {
        when:
            def finalPrice = itemPriceCalculator.calculateFinalPriceForItems(BigDecimal.valueOf(20), BigDecimal.valueOf(10))

        then:
            finalPrice == BigDecimal.valueOf(10)
    }

}
