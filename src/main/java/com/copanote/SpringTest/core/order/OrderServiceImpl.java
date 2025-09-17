package com.copanote.SpringTest.core.order;

import com.copanote.SpringTest.core.discount.DiscountPolicy;
import com.copanote.SpringTest.core.discount.FixDiscountPolicy;
import com.copanote.SpringTest.core.member.Member;
import com.copanote.SpringTest.core.member.MemberRepository;
import com.copanote.SpringTest.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
