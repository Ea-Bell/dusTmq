package com.example.dusTmq.repository.board.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class CommentImpl implements  ICommentRepository{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CommentImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


}
