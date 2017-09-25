package me.balukiewicz.checkout.item.controller;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import me.balukiewicz.checkout.item.dto.CheckoutReport;
import me.balukiewicz.checkout.item.ItemFacade;
import me.balukiewicz.checkout.item.exception.ItemNotFoundException;
import me.balukiewicz.checkout.item.dto.ItemQuantity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/checkout")
@AllArgsConstructor
@Log4j
class ItemCheckoutController {

    private final ItemFacade itemFacade;

    @PostMapping("/item")
    void storeItem(@RequestBody @Valid ItemQuantity itemQuantity) {
        itemFacade.store(itemQuantity);
    }

    @PostMapping
    CheckoutReport checkout() {
        return itemFacade.checkout();
    }

    @ExceptionHandler(ItemNotFoundException.class)
    ResponseEntity<String> handleItemNotFoundException(ItemNotFoundException itemNotFoundException) {
        log.error(itemNotFoundException.getMessage(), itemNotFoundException);
        return ResponseEntity.badRequest().body(itemNotFoundException.getMessage());

    }

}
