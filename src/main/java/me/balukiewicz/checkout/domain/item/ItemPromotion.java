package me.balukiewicz.checkout.domain.item;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

@Data
@Document
class ItemPromotion {

    @Id
    private String item_id_first;
    private String item_id_second;
    private Double discount;
}

interface ItemPromotionRepository extends MongoRepository<ItemPromotion, String> {

}
