package com.example.dusTmq.service.member;

import com.example.dusTmq.common.exception.MemberException;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void saveMember(Member member) throws MemberException {

        boolean duplication = memberRepository.existsByEmail(member.getAuth(), member.getEmail());
        if(!duplication){
            memberRepository.save(member);
            log.debug("memberSave={}", member.toString());
        }else {
            throw new MemberException("이메일이 중복입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
