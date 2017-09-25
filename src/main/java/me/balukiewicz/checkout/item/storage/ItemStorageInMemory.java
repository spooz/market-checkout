package me.balukiewicz.checkout.item.storage;

import com.google.common.collect.Maps;
import me.balukiewicz.checkout.item.exception.ItemNotFoundException;
import me.balukiewicz.checkout.item.domain.ItemRepository;
import me.balukiewicz.checkout.item.dto.ItemQuantity;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemStorageInMemory implements ItemStorage {

    private final ItemRepository itemRepository;
    private final Map<String, Long> items;

    public ItemStorageInMemory(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        this.items = Maps.newConcurrentMap();
    }

    @Override
    public void store(ItemQuantity itemQuantity) {
        if(!itemRepository.exists(itemQuantity.getId())) {
            throw new ItemNotFoundException(itemQuantity.getId());
        }

        if(items.putIfAbsent(itemQuantity.getId(), itemQuantity.getQuantity()) != null) {
            items.computeIfPresent(itemQuantity.getId(), (k, v) -> v + itemQuantity.getQuantity());
        }
    }

    @Override
    public Optional<Long> getQuantity(String id) {
        return items.containsKey(id) ? Optional.of(items.get(id)) : Optional.empty();
    }

    @Override
    public Set<ItemQuantity> getAll() {
        return items.entrySet()
                .stream()
                .map(entry -> new ItemQuantity(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    @Override
    public void clear() {
        items.clear();
    }


}


