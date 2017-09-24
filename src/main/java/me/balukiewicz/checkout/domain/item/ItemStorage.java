package me.balukiewicz.checkout.domain.item;

import java.util.List;
import java.util.Optional;
import java.util.Set;

interface ItemStorage {

    void store(String id, Long quantity);
    Optional<Long> getQuantity(String id);
    Set<ItemQuantity> getAll();
}
