package com.example.dusTmq.repository.member;

import com.example.dusTmq.domain.user.Authority;
import com.example.dusTmq.domain.user.Member;

public interface IMemberRepository {
    Member findMember(Authority authority, String email);
}
