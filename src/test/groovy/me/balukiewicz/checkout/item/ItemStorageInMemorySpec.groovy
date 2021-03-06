package me.balukiewicz.checkout.item

import me.balukiewicz.checkout.item.domain.ItemRepository
import me.balukiewicz.checkout.item.dto.ItemQuantity
import me.balukiewicz.checkout.item.exception.ItemNotFoundException
import me.balukiewicz.checkout.item.storage.ItemStorageInMemory
import spock.lang.Specification

class ItemStorageInMemorySpec extends Specification {

    def itemStorage
    def itemRepository

    def setup() {
        itemRepository = Stub(ItemRepository.class)
        itemStorage = new ItemStorageInMemory(itemRepository)
    }

    def "should store item with correct id and new quantity"() {
        given:
            def id = "id1"
            def quantity = 1L;
            def itemQuantity = new ItemQuantity(id, quantity)
            itemRepository.exists(id) >> true

        when:
            itemStorage.store(itemQuantity)

        then:
            itemStorage.getQuantity(id).get() == quantity
    }

    def "should store item with correct id and incremented quantity"() {
        given:
            def id = "id1"
            def quantity = 1L
            def newQuantity = 2L

            def itemQuantity = new ItemQuantity(id, quantity)
            def newItemQuantity = new ItemQuantity(id, newQuantity)

            itemRepository.exists(id) >> true
            itemStorage.store(itemQuantity)

        when:
            itemStorage.store(newItemQuantity)

        then:
            itemStorage.getQuantity(id).get() == quantity + newQuantity
    }

    def "should throw ItemNotFoundException when given wrong item id"() {
        given:
            def id = "id1"
            def quantity = 1L
            def itemQuantity = new ItemQuantity(id, quantity)
            itemRepository.exists(id) >> false

        when:
            itemStorage.store(itemQuantity)

        then:
            thrown ItemNotFoundException
    }


    def "should get quantity by existing id"() {
        given:
            def id = "id1"
            def quantity = 1L
            def itemQuantity = new ItemQuantity(id, quantity)
            itemRepository.exists(id) >> true
            itemStorage.store(itemQuantity)

        when:
            def returnedQuantity = itemStorage.getQuantity(id)

        then:
            returnedQuantity.get() == quantity
    }

    def "should return empty quantity when id not found"() {
        given:
            def id = "id1"

        when:
            def returnedQuantity = itemStorage.getQuantity(id)

        then:
            returnedQuantity.isPresent() == false
    }

}
