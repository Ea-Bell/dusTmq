package com.example.dusTmq.repository.member;

import com.example.dusTmq.domain.user.Authority;
import com.example.dusTmq.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


//간단한 쿼리는 Spring Data JPA로 처리할것.
public interface MemberRepository extends JpaRepository<Member, Long>, iMemberQuerydslRepository {


    @Query("select m from Member m where m.auth= :authority and m.email = :email")
    Optional<Member> findEmailInQuery(@Param("authority") String authority, @Param("email") String email);
    Member findByEmail(String email);

}