package me.balukiewicz.checkout.item.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String itemId) {
        super("Item with id:" + itemId + "not found");
    }
}
