package com.copanote.SpringTest.core.discount;

import com.copanote.SpringTest.core.member.Grade;
import com.copanote.SpringTest.core.member.Member;

public class RateDiscountPolicy implements DiscountPolicy {

    private final int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {

        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
