package me.balukiewicz.checkout.item;

import me.balukiewicz.checkout.item.dto.ItemQuantity;

import java.util.Optional;
import java.util.Set;

interface ItemStorage {
    void store(ItemQuantity itemQuantity);
    Optional<Long> getQuantity(String id);
    Set<ItemQuantity> getAll();
    void clear();
}
