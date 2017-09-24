package me.balukiewicz.checkout.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@Data
@AllArgsConstructor
@Document
class Item {

    @Id
    private String id;

    private Double price;

    private Long promUnit;

    private Double promPrice;
}

interface ItemRepository extends MongoRepository<Item, String> {

    Optional<Item> findById(String id);
}
