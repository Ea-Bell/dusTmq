package com.example.dusTmq.repository.member;

import com.example.dusTmq.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


//간단한 쿼리는 Spring Data JPA로 처리할것.
public interface MemberRepository extends JpaRepository<Member, Long>, iMemberQuerydslRepository {


//    @Query("select m from Member m where m.authority= :authority and m.email = :email")
//    Optional<Member> findEmailInQuery(@Param("authority") Authority authority, @Param("email") String email);


    Optional<Member> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE  Member m set m.lastLoginTime = :lastLoginTime where m.email = :email")
    void updateMemberLastLogin(@Param("email")String email, @Param("lastLoginTime") LocalDateTime lastLoginTime);
}