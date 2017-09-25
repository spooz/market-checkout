package me.balukiewicz.checkout.item.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

@Data
@Document
public class ItemPromotion {

    @Id
    private String item_id_first;
    private String item_id_second;
    private Double discount;
}

