package com.copanote.SpringTest.core.member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long member);

}
