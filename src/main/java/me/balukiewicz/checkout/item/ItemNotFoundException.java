package me.balukiewicz.checkout.item;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String s) {
        super(s);
    }
}
