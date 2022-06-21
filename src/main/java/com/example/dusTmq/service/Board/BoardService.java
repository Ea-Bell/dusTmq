package com.example.dusTmq.service.Board;

import com.example.dusTmq.domain.board.BoardDetailVO;
import com.example.dusTmq.domain.board.viewDto.BoardDTO;
import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.repository.board.BoardRepository;
import com.example.dusTmq.repository.member.MemberRepository;
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
    private final MemberRepository memberRepository;

//    @Transactional
//    @Override
//    public void boardSave(BoardDTO boardDTO, Member member){
//
//        BoardDetailVO boardDetailVO = BoardDetailVO.builder().detail(boardDTO.getDetail())
//                .title(boardDTO.getTitle())
//                .createDate(LocalDateTime.now())
//                .createUserName(member)
//                .deleteDate(LocalDateTime.of(1900, 01,01,00,00,00))
//                .deleteUserName("")
//                .updateDate(LocalDateTime.now())
//                .build();
//        boardDetailRepository.save(boardDetailVO);
//
//        log.debug("saveBoard={}", boardDetailVO);
//    }



    @Transactional
    @Override
    public void boardSave(BoardDTO boardDTO, String email){

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new NullPointerException("Not Email Exist"));

        BoardDetailVO boardDetailVO = BoardDetailVO.builder().detail(boardDTO.getDetail())
                .title(boardDTO.getTitle())
                .createDate(LocalDateTime.now())
                .createUserName(member)
                .deleteDate(LocalDateTime.of(1900, 01,01,00,00,00))
                .deleteUserName("")
                .updateDate(LocalDateTime.now())
                .build();
        boardDetailRepository.save(boardDetailVO);

        log.debug("saveBoard={}", boardDetailVO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardDetailVO> findAllBoard(BoardDetailVO boardDetailVO){
        return boardDetailRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardDetailVO> findAllBoard(){return  boardDetailRepository.findAll();}

    @Override
    @Transactional(readOnly = true)
    public Page<BoardListDTO> pagingBoardListBy( Pageable pageable){
        return boardDetailRepository.pagingBoardListBy(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardDetailVO> findByBoardList(PageRequest pageRequest){
        return boardDetailRepository.findAll(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BoardDetailVO> getByIdBoard(Long id) {
        return boardDetailRepository.findById(id);
    }

    @Transactional
    @Override
    public void updateByBoard(Long id, BoardDTO boardDTO) throws Exception {
        BoardDetailVO boardDetailVO = boardDetailRepository.findById(id).orElseThrow(Exception::new);
        boardDetailVO.updateBoardDetail(boardDTO.getTitle(), boardDTO.getDetail(), LocalDateTime.now(), boardDetailVO.getMember());
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
