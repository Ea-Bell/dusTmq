package com.example.dusTmq.repository.member;

import com.example.dusTmq.domain.user.Authority;
import com.example.dusTmq.domain.user.Member;

import java.util.Optional;

public interface IMemberRepository {
    Optional<Member> findMember(Authority authority, String email);
}
