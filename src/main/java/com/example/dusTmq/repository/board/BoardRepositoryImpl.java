package com.example.dusTmq.repository.board;


import com.example.dusTmq.domain.board.BoardDetailVO;
import com.example.dusTmq.domain.board.QBoardDetailVO;
import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import com.example.dusTmq.domain.user.QMember;
import com.example.dusTmq.domain.user.dto.MemberRegisterDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class BoardRepositoryImpl  implements IBoardRepository{
    private final JPAQueryFactory queryFactory;
    @Autowired
    public BoardRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<BoardListDTO> pagingBoardListBy(Pageable pageable){

        QBoardDetailVO boardDetailVO = QBoardDetailVO.boardDetailVO;
        List<BoardListDTO> content =
                queryFactory
                        .select(
                        Projections.bean(BoardListDTO.class,
                                boardDetailVO.id, boardDetailVO.title, boardDetailVO.member.email.as("email"), boardDetailVO.createDate))
                .orderBy(boardDetailVO.id.desc())
                        .from(boardDetailVO)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

       //conunt 쿼리 분리
        JPQLQuery<BoardListDTO> count =
            queryFactory
                    .select(Projections.bean(BoardListDTO.class,
                        boardDetailVO.id, boardDetailVO.title, boardDetailVO.member.email.as("email"), boardDetailVO.createDate))
                        .from(boardDetailVO);


        return PageableExecutionUtils.getPage(content, pageable, ()->count.fetchCount());
    }

    /**
     * @param email memberEmail
     * @param id boardId
     * @return 존재하면 true 존재하지 않으면 fasle
     */
    @Override
    public boolean matchEmailAndBoardEmailId(String email, Long id){

        QBoardDetailVO boardDetailVO = QBoardDetailVO.boardDetailVO;
        //객체끼리의 joinQuery 의 다른 방법
    //        QMember member = QMember.member;
    //        Long query = queryFactory.select(boardDetailVO.createUserName.count())
    //                .from(boardDetailVO)
    //                .where(boardDetailVO.id.eq(id)
    //                        .and(boardDetailVO.createUserName.in(JPAExpressions
    //                        .select(member.email)
    //                        .from(member)
    //                        .where(member.email.eq(email)))))
    //                .fetchOne();
    //         if(query >=1){
    //             return true;
    //         }
    //         return false;
        Long query = queryFactory.select(boardDetailVO.member.count())
                .from(boardDetailVO)
                .where(boardDetailVO.id.eq(id)
                        .and(boardDetailVO.member.email.eq(email)))
                .fetchOne();
         if(query >=1){
             return true;
         }
         return false;
    }
}
