package com.example.dusTmq.repository.member;

import com.example.dusTmq.domain.user.Authority;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.user.QMember;
import com.querydsl.core.types.Projections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class MemberRepositoryImpl extends QuerydslRepositorySupport implements IMemberRepository {
    public MemberRepositoryImpl() {
        super(Member.class);
    }


    @Override
    public Optional<Member> findMember(Authority authority, String email){
        QMember member = QMember.member;
       return Optional.ofNullable(from(member)
               .select(Projections.fields(Member.class
                               , member.authority
                               , member.email
                       )
               )
               .where(member.authority.eq(authority), member.email.eq(email))
               .fetchOne());
    }

}
