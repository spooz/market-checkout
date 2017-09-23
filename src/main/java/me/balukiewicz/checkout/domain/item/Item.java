package me.balukiewicz.checkout.domain.item;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

@Data
@Document
class Item {

    @Id
    private String id;

    private Double price;

    private Long promUnit;

    private Double promPrice;
}

interface ItemRepository extends MongoRepository<Item, String> {

}
