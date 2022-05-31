package com.example.dusTmq.service.member;

import com.example.dusTmq.common.exception.MemberException;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.user.MemberDetail;
import com.example.dusTmq.domain.user.dto.MemberSessionDTO;
import com.example.dusTmq.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Transactional
    @Override
    public void saveMember(Member member) throws MemberException {

//        boolean duplication = memberRepository.duplicationCheckByMember(member.getAuthority(), member.getEmail());
//        if(!duplication){
//            memberRepository.save(member);
//            log.debug("memberSave={}", member.toString());
//        }else {
//            throw new MemberException("이메일이 중복입니다.");
//        }

         memberRepository.save(member);
    }

    @Transactional
    @Override
    public void updateMemberLastLogin(String email, LocalDateTime localDateTime){
        memberRepository.updateMemberLastLogin(email, localDateTime);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        memberRepository.updateMemberLastLogin(email, LocalDateTime.now());
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not Found account."));

        log.debug("member={}",member.toString());
        session.setAttribute("member", new MemberSessionDTO(member));
        log.debug("session={}", session.getAttribute("member").toString());
        return new MemberDetail(member);
    }
}
