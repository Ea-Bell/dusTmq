package com.example.dusTmq.service.member;

import com.example.dusTmq.common.exception.MemberException;
import com.example.dusTmq.domain.user.Authority;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.user.dto.MemberDTO;
import com.example.dusTmq.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void saveUserMember(MemberDTO memberDTO) throws MemberException {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        memberDTO.setPwd(encoder.encode(memberDTO.getPwd()));
        boolean duplication = memberRepository.existsByEmail(memberDTO.getEmail());

        if(!duplication){
            Member member = Member.builder()
                    .email(memberDTO.getEmail())
                    .password(memberDTO.getPwd())
                    .regDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .deleteTime(LocalDateTime.of(1900,01,01,00,00,00))
                    .username(memberDTO.getName())
                    .age(memberDTO.getAge())
                    .gender(memberDTO.getSex())
                    .auth(Authority.ROLL_USER.toString())
                    .build();
            memberRepository.save(member);
            log.debug("memberSave={}", member.toString());

        }else {
            throw new MemberException("이메일이 중복입니다.");
        }


    }

    //스프링이 로그인 요청을 가로챌때 email, password 변수 2개를 가로챘는데
    // password 부분 처리는 알아서 처리,
    // email 이 DB에 있는지 확인 여부 필요
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을수 없습니다. :"+email));

        log.debug("member={}",member);
        return member;


    }
}
