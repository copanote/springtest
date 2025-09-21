package com.copanote.SpringTest.core;

import com.copanote.SpringTest.core.discount.DiscountPolicy;
import com.copanote.SpringTest.core.discount.FixDiscountPolicy;
import com.copanote.SpringTest.core.member.MemberRepository;
import com.copanote.SpringTest.core.member.MemberService;
import com.copanote.SpringTest.core.member.MemberServiceImpl;
import com.copanote.SpringTest.core.member.MemoryMemberRepository;
import com.copanote.SpringTest.core.order.OrderService;
import com.copanote.SpringTest.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memoryMemberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(
                memoryMemberRepository(),
                discountPolicy());
    }

    @Bean
    public MemberRepository memoryMemberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }

}
