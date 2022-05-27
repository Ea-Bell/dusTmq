package com.example.dusTmq.repository.member;

import com.example.dusTmq.domain.user.Authority;
import com.example.dusTmq.domain.user.Member;

import java.util.Optional;

public interface iMemberQuerydslRepository {

    Optional<Member> findMember(String authority, String email);

    //중복 계정 찾기 요소가 반드시 1개라서 get(0)해서 Long 타입으로 넘겨줌.
    boolean existsByEmail(String authority, String email);
    boolean existsByEmail(String email);
    //TODO 2022-05-26 최적화 생각해보기
    //권한, 이메일을 통해 Member 찾기


}
