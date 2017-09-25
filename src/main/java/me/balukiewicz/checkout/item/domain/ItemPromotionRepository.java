package me.balukiewicz.checkout.item.domain;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemPromotionRepository extends MongoRepository<ItemPromotion, String> {
}