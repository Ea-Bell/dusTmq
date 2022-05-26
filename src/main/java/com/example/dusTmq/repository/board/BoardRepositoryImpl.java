package com.example.dusTmq.repository.board;



import com.example.dusTmq.domain.board.QBoardDetailVO;
import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class BoardRepositoryImpl extends QuerydslRepositorySupport implements IBoardRepository{



    public BoardRepositoryImpl() {
        super(BoardListDTO.class);
    }

    @Override
    public Page<BoardListDTO> pagingBoardListBy(Pageable pageable){

        QBoardDetailVO boardDetailVO = QBoardDetailVO.boardDetailVO;
        List<BoardListDTO> content = from(boardDetailVO)
                .select(
                        Projections.bean(BoardListDTO.class,
                                boardDetailVO.id, boardDetailVO.title, boardDetailVO.createUserName, boardDetailVO.createDate))
                .orderBy(boardDetailVO.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

       //conunt 쿼리 분리
        JPQLQuery<BoardListDTO> count = from(boardDetailVO)
                .select(Projections.bean(BoardListDTO.class,
                        boardDetailVO.id, boardDetailVO.title, boardDetailVO.createUserName, boardDetailVO.createDate));


        return PageableExecutionUtils.getPage(content, pageable, ()->count.fetchCount());
    }
}
