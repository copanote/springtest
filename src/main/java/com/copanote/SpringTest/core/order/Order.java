package com.copanote.SpringTest.core.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Order {
    private final Long memberId;
    private final String itemName;
    private final int itemPrice;
    private final int discountPrice;


    public int calculatePrice() {
        return itemPrice - discountPrice;
    }

}
