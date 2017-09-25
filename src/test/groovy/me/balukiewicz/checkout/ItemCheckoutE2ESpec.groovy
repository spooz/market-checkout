package me.balukiewicz.checkout

import me.balukiewicz.checkout.item.domain.Item
import me.balukiewicz.checkout.item.domain.ItemRepository
import me.balukiewicz.checkout.item.dto.ItemQuantity
import me.balukiewicz.checkout.item.storage.ItemStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class ItemCheckoutE2ESpec extends MockMvcSpec   {

    @Autowired
    ItemRepository itemRepository

    @Autowired
    ItemStorage itemStorage

    def "should store item quantity with proper id"() {
        given:
            itemRepository.save(new Item("id1", BigDecimal.ONE, false, 0, BigDecimal.ZERO))
            def itemQuantity = new ItemQuantity("id1", 10)

        when:
            def response = mockMvc.perform(post('/api/v1/checkout/item')
            .content(toJson(itemQuantity)).contentType(MediaType.APPLICATION_JSON))
            .andReturn().response

        then:
            response.status == 200
            itemStorage.getQuantity("id1").get() == 10

        cleanup:
            itemRepository.deleteAll()
            itemStorage.clear()
    }

    def "should response bad request when storing item quantity with wrong id"() {
        given:
            def itemQuantity = new ItemQuantity("id1", 10)

        when:
            def response = mockMvc.perform(post('/api/v1/checkout/item')
                    .content(toJson(itemQuantity)).contentType(MediaType.APPLICATION_JSON))
                    .andReturn().response

        then:
            response.status == 400
    }

}
