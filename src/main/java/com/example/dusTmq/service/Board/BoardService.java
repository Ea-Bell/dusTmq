package com.example.dusTmq.service.Board;

import com.example.dusTmq.domain.board.BoardDetailVO;
import com.example.dusTmq.domain.board.viewDto.BoardDTO;
import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import com.example.dusTmq.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService implements IBoard{

    private final BoardRepository boardDetailRepository;

    @Transactional
    @Override
    public void boardSave(BoardDTO boardDTO){

        BoardDetailVO boardDetailVO = BoardDetailVO.builder().detail(boardDTO.getDetail())
                .title(boardDTO.getTitle())
                .createDate(LocalDateTime.now())
                .createUserName("김아무개")
                .deleteDate(LocalDateTime.of(1900, 01,01,00,00,00))
                .deleteUserName("")
                .build();
        boardDetailRepository.save(boardDetailVO);

        log.debug("saveBoard={}", boardDetailVO);
    }

    @Override
    @Transactional
    public List<BoardDetailVO> findAllBoard(BoardDetailVO boardDetailVO){
        return boardDetailRepository.findAll();
    }

    @Override
    @Transactional
    public List<BoardDetailVO> findAllBoard(){return  boardDetailRepository.findAll();}

    @Override
    @Transactional
    public Page<BoardListDTO> pagingBoardListBy( Pageable pageable){

    return boardDetailRepository.pagingBoardListBy(pageable);
    }

    @Override
    @Transactional
    public Page<BoardDetailVO> findByBoardList(PageRequest pageRequest){
        return boardDetailRepository.findAll(pageRequest);
    }

    @Override
    @Transactional
    public Optional<BoardDetailVO> getByIdBoard(Long id) {
        return boardDetailRepository.findById(id);
    }

    @Transactional
    @Override
    public void updateByBoard(Long id, BoardDTO boardDTO) throws Exception {
        BoardDetailVO boardDetailVO = boardDetailRepository.findById(id).orElseThrow(Exception::new);
        boardDetailVO.updateBoardDetail(boardDTO.getTitle(), boardDTO.getDetail(), LocalDateTime.now(), "아무개씨");
        log.debug("boardDetailVO={}",boardDetailVO);
    }

    @Override
    @Transactional
    public void deleteByBoard(Long id){
        boardDetailRepository.deleteById(id);
        log.debug("deleteByBoardById={}", id);
    }
    @Override
    @Transactional
    public void deleteByBoardAll(List<Long> ids){
        boardDetailRepository.deleteAllByIdInQuery(ids);
        log.debug("deleteIds ={}", ids.toString());
    }
}
