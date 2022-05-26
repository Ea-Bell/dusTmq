package com.example.dusTmq.repository.board;


import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBoardRepository {


    public Page<BoardListDTO> pagingBoardListBy(Pageable pageable);
}
