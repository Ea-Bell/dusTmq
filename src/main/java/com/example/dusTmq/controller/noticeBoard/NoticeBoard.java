package com.example.dusTmq.controller.noticeBoard;

import com.example.dusTmq.common.Message;
import com.example.dusTmq.common.StatusEnum;
import com.example.dusTmq.common.exception.CommonException;
import com.example.dusTmq.domain.board.BoardDetailVO;
import com.example.dusTmq.domain.board.viewDto.BoardDTO;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.user.dto.MemberSessionDTO;
import com.example.dusTmq.service.Board.IBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

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
    public String boardEdit(@PathVariable("id") long id, HttpServletRequest request,  Model mv) throws Exception {
        //최적화 필요 "필요 없는 데이터까지 가지고옴"
        Optional<BoardDetailVO> byIdBoard = boardService.getByIdBoard(id);
        BoardDetailVO boardDetailVO= byIdBoard.orElseThrow(Exception :: new);

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle(boardDetailVO.getTitle());
        boardDTO.setDetail(boardDetailVO.getDetail());

        mv.addAttribute("boardDTO", boardDTO);
        mv.addAttribute("id",id);

        return noticeBoardEdit;
    }

    @PostMapping("/{id}")
    public String boardEdit(@ModelAttribute("boardDTO") @Validated BoardDTO boardDTO, BindingResult bindingResult,@PathVariable("id") long id, HttpServletRequest request, Model mv) throws Exception {
        HttpSession session = request.getSession();
        MemberSessionDTO memberSessionDTO = (MemberSessionDTO) session.getAttribute("member");

        log.debug("memberSessionDTO={}", memberSessionDTO.toString());

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
        Message errorMsg = new Message();
        FieldError fieldError = bindingResult.getFieldError();

        errorMsg.setStatus(StatusEnum.BAD_REQUEST);
        errorMsg.setMessage(fieldError.getDefaultMessage());
        errorMsg.setData(fieldError.getField());

        log.error("errorMsg={}", errorMsg);
        return errorMsg;
    }



    //공통처리할 bindingResult.hasErrors()를 처리할 방법을 생각해야함.

}
