package me.balukiewicz.checkout

import groovy.json.JsonSlurper
import me.balukiewicz.checkout.item.domain.Item
import me.balukiewicz.checkout.item.domain.ItemPromotion
import me.balukiewicz.checkout.item.domain.ItemPromotionRepository
import me.balukiewicz.checkout.item.domain.ItemRepository
import me.balukiewicz.checkout.item.dto.ItemQuantity
import me.balukiewicz.checkout.item.storage.ItemStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class CheckoutE2ESpec extends MockMvcSpec{

    @Autowired
    ItemPromotionRepository itemPromotionRepository

    @Autowired
    ItemRepository itemRepository

    @Autowired
    ItemStorage itemStorage

    def "should return checkout report"() {
        given:
            itemRepository.save(new Item("id1", BigDecimal.ONE, true, 4,BigDecimal.valueOf(2)))
            itemRepository.save(new Item("id2", BigDecimal.ONE, false, 0, BigDecimal.ZERO))
            itemRepository.save(new Item("id3", BigDecimal.ONE, false, 0, BigDecimal.ZERO))

            itemPromotionRepository.save(new ItemPromotion("id1", "id3", BigDecimal.valueOf(5)))

            def itemQuantity1 = new ItemQuantity("id1", 10)
            def itemQuantity2 = new ItemQuantity("id2", 5)
            def itemQuantity3 = new ItemQuantity("id3", 3)

            itemStorage.store(itemQuantity1)
            itemStorage.store(itemQuantity2)
            itemStorage.store(itemQuantity3)

        when:
            def response = mockMvc.perform(post('/api/v1/checkout'))
                    .andReturn().response

            def content = new JsonSlurper().parseText(response.contentAsString)

        then:
            response.status == 200
            content.itemFinalPrices.size() == 3
            assertItemFinalPrices(content.itemFinalPrices)

            content.promotion == 5.0
            content.finalPrice == 9.0

        cleanup:
            itemRepository.deleteAll()
            itemPromotionRepository.deleteAll()
            itemStorage.clear()

    }

    private def assertItemFinalPrices(itemFinalPrices) {
        itemFinalPrices.any {
            it.id == "id1"
            it.quantity == 10
            it.finalPrice = 6.0
        }

        itemFinalPrices.any {
            it.id == "id2"
            it.quantity == 3
            it.finalPrice = 3.0
        }

        itemFinalPrices.any {
            it.id == "id3"
            it.quantity == 5
            it.finalPrice = 5.0
        }

    }

}
