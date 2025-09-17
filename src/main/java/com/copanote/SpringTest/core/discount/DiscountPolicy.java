package com.copanote.SpringTest.core.discount;

import com.copanote.SpringTest.core.member.Member;

public interface DiscountPolicy {
    int discount(Member member, int price);
}
