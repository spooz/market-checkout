package me.balukiewicz.checkout.domain.item

import spock.lang.Specification

class InMemoryItemStorageTest extends Specification {

    def itemStorage
    def itemRepository

    def setup() {
        itemRepository = Stub(ItemRepository.class)
        itemStorage = new InMemoryItemStorage(itemRepository)
    }

    def "should store item with correct id and new quantity"() {
        given:
            def id = "id1"
            def quantity = 1L;
            itemRepository.exists(id) >> true

        when:
            itemStorage.store(id, quantity)

        then:
            itemStorage.getQuantity(id).get() == quantity
    }

    def "should store item with correct id and incremented quantity"() {
        given:
            def id = "id1"
            def quantity = 1L
            def newQuantity = 2L

            itemRepository.exists(id) >> true
            itemStorage.store(id, quantity)

        when:
            itemStorage.store(id, newQuantity)

        then:
            itemStorage.getQuantity(id).get() == quantity + newQuantity
    }

    def "should throw ItemNotFoundException when given wrong item id"() {
        given:
            def id = "id1"
            def quantity = 1L

            itemRepository.exists(id) >> false

        when:
            itemStorage.store(id, quantity)

        then:
            thrown ItemNotFoundException
    }


    def "should get quantity by existing id"() {
        given:
            def id = "id1"
            def quantity = 1L

            itemRepository.exists(id) >> true
            itemStorage.store(id, quantity)

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
    /*
    def "should return stored item quantities"() {
        given:
            def id1 = "id1"
            def quantity1 = 1L
            def id2 = "id2"
            def quantity2 = 1L

            itemRepository.exists(id1) >> true
            itemRepository.exists(id2) >> true

            itemStorage.store(id1, quantity1)
            itemStorage.store(id2, quantity2)

        when:
            def returnedQuantities = itemStorage.getAll()

        then:

    }          */

}
