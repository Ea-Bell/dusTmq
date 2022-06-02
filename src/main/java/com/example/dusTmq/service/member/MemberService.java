package com.example.dusTmq.service.member;

import com.example.dusTmq.common.exception.MemberException;
import com.example.dusTmq.domain.user.Member;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface MemberService extends UserDetailsService {
    @Transactional
    void saveMember(Member member) throws MemberException;

    @Transactional
    void updateMemberLastLogin(String email, LocalDateTime localDateTime);
}
