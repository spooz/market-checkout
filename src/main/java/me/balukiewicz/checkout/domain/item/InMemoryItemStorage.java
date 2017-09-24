package me.balukiewicz.checkout.domain.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class InMemoryItemStorage implements ItemStorage {

    private final ItemRepository itemRepository;
    private final Map<String, Long> items;

    InMemoryItemStorage(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        this.items = Maps.newConcurrentMap();
    }

    @Override
    public void store(String id, Long quantity) {
        if(!itemRepository.exists(id)) {
            throw new ItemNotFoundException("Item with id:" + id + " not found");
        }

        if(items.putIfAbsent(id, quantity) != null) {
            items.computeIfPresent(id, (k, v) -> v + quantity);
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
}


