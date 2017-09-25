package me.balukiewicz.checkout.item.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Document
public class ItemPromotion {

    @Id
    private String itemIdFirst;

    @NotNull
    private String itemIdSecond;

    @NotNull
    private BigDecimal discount;
}

