package me.balukiewicz.checkout.item.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

@Data
@AllArgsConstructor
@Document
public class Item {

    @Id
    private String id;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Boolean hasPromotion;

    private Long promotionUnit;
    private BigDecimal promotionPrice;
}