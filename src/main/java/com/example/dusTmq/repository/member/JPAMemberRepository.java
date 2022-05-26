package com.example.dusTmq.repository.member;

import com.example.dusTmq.domain.user.Authority;
import com.example.dusTmq.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JPAMemberRepository extends JpaRepository<Member, Long> {


    @Query("select m from Member m where m.authority= :authority and m.email = :email")
    Optional<Member> findEmailInQuery(@Param("authority") Authority authority, @Param("email") String email);

}