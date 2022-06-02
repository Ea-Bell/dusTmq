package com.example.dusTmq.repository.member;


import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.user.Role;

import java.util.Optional;

public interface iMemberQuerydslRepository {

    Optional<Member> findMember(Role role, String email);

    //중복 계정 찾기 요소가 반드시 1개라서 get(0)해서 Long 타입으로 넘겨줌.
    boolean duplicationCheckByMember(Role role, String email);

}
