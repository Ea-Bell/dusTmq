package com.example.dusTmq.service.member;

import com.example.dusTmq.common.exception.MemberException;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.repository.member.iMemberQuerydslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements  IMember{


    @Transactional
    public void saveMember(Member member) throws MemberException {
       // boolean duplication = memberRepository.duplicationCheckByMember(member.getAuthority(), member.getEmail());

//        if(!duplication){
//
//        }
//        throw new MemberException("이메일이 중복입니다.");

        //member의 이메일과, 권한을 확인 후 중복이 안되면 save 중복이 되면 error출력
    }

}
