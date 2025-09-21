package com.copanote.SpringTest.jpa.repository;

import com.copanote.SpringTest.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

//    @Query(name = "Member.findByUsername")
//    List<Member> findByUsername(@Param("username") String username);

    /*
       스프링 데이터 JPA는 선언한 도메인클래스.메서드이름 으로 Named쿼리를 찾아서 실행.
       만약 실행할 Named쿼리가 없으면 메서드 이름으로 쿼리 생성전략을 사용한다.
       필요하면 전략을 변경할 수 있지만 권장하지 않는다.
     */
//    List<Member> findByUsername(@Param("username") String username);


    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);


    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();


}