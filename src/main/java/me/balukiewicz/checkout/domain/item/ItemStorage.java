package me.balukiewicz.checkout.domain.item;

import java.util.List;
import java.util.Optional;

interface ItemStorage {

    void store(String id, Long quantity);
    Optional<Long> getQuantity(String id);
    List<ItemQuantity> getAll();
}
