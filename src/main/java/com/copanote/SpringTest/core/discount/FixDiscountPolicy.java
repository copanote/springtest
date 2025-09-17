package com.copanote.SpringTest.core.discount;

import com.copanote.SpringTest.core.member.Grade;
import com.copanote.SpringTest.core.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {

        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
