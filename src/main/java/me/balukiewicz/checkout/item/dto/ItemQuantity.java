package me.balukiewicz.checkout.item.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ItemQuantity {
    @NotNull
    private String id;
    @NotNull
    private Long quantity;
}