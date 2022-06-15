package com.example.dusTmq.controller.noticeBoard;

import com.example.dusTmq.common.Message;
import com.example.dusTmq.common.StatusEnum;
import com.example.dusTmq.common.exception.CommonException;
import com.example.dusTmq.common.exception.ErrorResult;
import com.example.dusTmq.domain.board.BoardDetailVO;
import com.example.dusTmq.domain.board.viewDto.BoardDTO;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.user.dto.MemberSessionDTO;
import com.example.dusTmq.service.Board.IBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


import static com.example.dusTmq.common.Message.getMessage;

@Controller
@Slf4j
@RequestMapping("/noticeBoard")
@RequiredArgsConstructor
public class NoticeBoard {


    private final IBoard boardService;
    private final static String noticeBoardEdit = "/noticeBoard/noticeBoardEdit";
    private final static String noticeBoard = "/noticeBoard/noticeBoard";
    private final static String noticeBoardAdd = "/noticeBoard/noticeBoardRegister";

    @GetMapping("/noticeBoardRegister")
    public String boardAdd(Model mv, HttpServletRequest request){
        BoardDTO boardDTO = new BoardDTO();
        mv.addAttribute(boardDTO);
        return noticeBoardAdd;
    }
    @PostMapping(value = "/noticeBoardRegister")
    public String boardAdd(@Validated @ModelAttribute("boardDTO") BoardDTO boardDTO, BindingResult bindingResult, HttpServletRequest request, Model mv) throws CommonException {

        HttpSession session = request.getSession();
        MemberSessionDTO member = (MemberSessionDTO) session.getAttribute("member"); //캐스캐이팅 조심
        log.debug("session={}",member.toString());

        if(bindingResult.hasErrors()){
            Message errorMsg = boardError(bindingResult);
            mv.addAttribute("errorMsg", errorMsg);
            return noticeBoardAdd;
        }

        boardService.boardSave(boardDTO, member.getEmail());
        return "redirect:/noticeBoard";
    }

    @GetMapping("/{id}")
    public String boardDetail(@PathVariable("id") long id, HttpServletRequest request,  Model mv) throws Exception {
        //최적화 필요 "필요 없는 데이터까지 가지고옴"
        BoardDetailVO boardDetailVO = boardService.getByIdBoard(id).orElseThrow(() -> new NoSuchElementException("Not Found Account"));

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle(boardDetailVO.getTitle());
        boardDTO.setDetail(boardDetailVO.getDetail());

        mv.addAttribute("boardDTO", boardDTO);
        mv.addAttribute("id",id);

        return noticeBoard;
    }
    @GetMapping("/modify/{id}")
    public String boardEdit(@PathVariable("id") long id, HttpServletRequest request,  Model mv) throws CommonException {
        log.debug("NoticeBoard.boardEdit()");
        Map<String, Object>result = new HashMap<>();
        //최적화 필요 "필요 없는 데이터까지 가지고옴"
        BoardDetailVO boardDetailVO = boardService.getByIdBoard(id).orElseThrow(() -> new NoSuchElementException("Not Found Account"));
        HttpSession session = request.getSession();
        MemberSessionDTO member = (MemberSessionDTO) session.getAttribute("member");

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle(boardDetailVO.getTitle());
        boardDTO.setDetail(boardDetailVO.getDetail());

        mv.addAttribute("boardDTO", boardDTO);
        mv.addAttribute("id",id);

        //비교해서 맞는 아이디면 수정으로 넘어가고 아니면 "인증되어지지 않는 유저입니다."라는 문구를 띄우자.
        if(!boardDetailVO.getMember().getEmail().equals(member.getEmail())){
            Message errorMsg = boardError(boardDTO);
            result.put("errorMsg", errorMsg);
            mv.addAttribute("errorMsg", errorMsg);
            log.debug("mv={}", mv.toString());
            return noticeBoard;
        }
            return noticeBoardEdit;
    }

    @PostMapping("/modify/{id}")
    public String boardEdit(@ModelAttribute("boardDTO") @Validated BoardDTO boardDTO, BindingResult bindingResult, @PathVariable("id") long id, HttpServletRequest request, Model mv) throws Exception {
        HttpSession session = request.getSession();
        MemberSessionDTO memberSessionDTO = (MemberSessionDTO) session.getAttribute("member");

        if(bindingResult.hasErrors()){
            Message errorMsg = boardError(bindingResult);
            mv.addAttribute("errorMsg", errorMsg);
            return noticeBoardEdit;
        }
        boardService.updateByBoard(id, boardDTO);
        mv.addAttribute("boardDTO", boardDTO);
        return noticeBoardEdit;
    }

    @PostMapping("/delete/{id}")
    public String boardDelete(@PathVariable("id")long id){
        boardService.deleteByBoard(id);

        return "redirect:/noticeBoard";
    }
    @PostMapping("/delete")
    public String boardDelete(@RequestParam("checkbox") List<Long> ids){
        boardService.deleteByBoardAll(ids);

        return "redirect:/noticeBoard";
    }

    private Message boardError(BindingResult bindingResult) {
        return getMessage(bindingResult, log);
    }

    private Message boardError(BoardDTO boardDTO){
        return getMessage(boardDTO, log);
    }
    //공통처리할 bindingResult.hasErrors()를 처리할 방법을 생각해야함.

}
