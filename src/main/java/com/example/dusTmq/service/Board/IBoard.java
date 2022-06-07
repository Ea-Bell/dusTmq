package com.example.dusTmq.service.Board;

import com.example.dusTmq.domain.board.BoardDetailVO;
import com.example.dusTmq.domain.board.viewDto.BoardDTO;
import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.user.dto.MemberSessionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IBoard {


    @Transactional
    void boardSave(BoardDTO boardDTO, String email);

    @Transactional
    List<BoardDetailVO> findAllBoard(BoardDetailVO boardDetailVO);
    @Transactional
    List<BoardDetailVO> findAllBoard();

    @Transactional
    Page<BoardListDTO> pagingBoardListBy(Pageable pageable);

    @Transactional
    Page<BoardDetailVO> findByBoardList(PageRequest pageRequest);

    @Transactional
    Optional<BoardDetailVO> getByIdBoard(Long id);

    @Transactional
    void updateByBoard(Long id, BoardDTO boardDTO) throws Exception;

    @Transactional
    void deleteByBoard(Long id);

    @Transactional
    void deleteByBoardAll(List<Long> ids);
}
