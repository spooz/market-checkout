package me.balukiewicz.checkout.domain.item;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String s) {
        super(s);
    }
}
