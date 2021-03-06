package com.example.dusTmq.repository.member;

import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.user.QMember;
import com.example.dusTmq.domain.user.Role;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class MemberRepositoryImpl implements iMemberQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    //TODO 2022-05-26 최적화 생각해보기
    //권한, 이메일을 통해 Member 찾기
    @Override
    public Optional<Member> findMember(Role role, String email){
//        QMember member = QMember.member;
//       return Optional.ofNullable(
//               queryFactory.select(Projections.fields(Member.class
//                       , member.authority
//                       , member.email
//               )
//       )
//               .from(member)
//               .where(member.authority.eq(role), member.email.eq(email))
//               .fetchOne());
        return null;
    }


    //중복이면 true 아니면 false
    @Override
    public boolean duplicationCheckByMember(Role role, String email){
        QMember member= QMember.member;
        Long count = queryFactory.select(
                        member.count()
                )
                .from(member)
                .where(member.role.eq(role), member.email.eq(email)).fetchOne();

        //값이 존재하면 true
       if(count >= 1){
           throw  new IllegalArgumentException("Email is Exist");
       }
       return false;
    }

}
