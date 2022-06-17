package com.example.dusTmq.controller.noticeBoard;

import com.example.dusTmq.common.Message;
import com.example.dusTmq.common.StatusEnum;
import com.example.dusTmq.common.exception.CommonException;
import com.example.dusTmq.domain.board.BoardDetailVO;
import com.example.dusTmq.domain.board.viewDto.BoardDTO;
import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import com.example.dusTmq.domain.user.dto.MemberSessionDTO;
import com.example.dusTmq.service.Board.IBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;



@RestController
@Slf4j
@RequestMapping("/noticeBoard")
@RequiredArgsConstructor
public class NoticeBoard {


    private final IBoard boardService;
    private final static String noticeBoardEdit = "/noticeBoard/noticeBoardEdit";
    private final static String noticeBoard = "/noticeBoard/noticeBoard";
    private final static String noticeBoardAdd = "/noticeBoard/noticeBoardRegister";

    @GetMapping
    public ModelAndView tables(@PageableDefault(size = 100000, sort = "id", direction = Sort.Direction.DESC ) Pageable pageable){
        Message message = new Message();
        Map<String, Object> resultMap = new HashMap<>();
        ModelAndView mv = new ModelAndView();
        Page<BoardListDTO> boardListDTOS = boardService.pagingBoardListBy(pageable);

        message.setData(boardListDTOS);
        resultMap.put("message", message);

        mv.addObject("result", resultMap);
        mv.setViewName("/noticeBoard/noticeBoardList");
        return mv;
    }

    @GetMapping("/noticeBoardRegister")
    public ModelAndView boardAdd(){
        ModelAndView mv = new ModelAndView();
        log.debug("NoticeBoard.boardAdd()");
        Message message = new Message();
        Map<String, Object> result = new HashMap<>();
        BoardDTO boardDTO = new BoardDTO();

        message.setData(boardDTO);
        result.put("message", message);

        mv.addObject("result", result);
        mv.setViewName(noticeBoardAdd);
        return mv;
    }
    @PostMapping(value = "/noticeBoardRegister")
    public ModelAndView boardAdd(@Validated @ModelAttribute("boardDTO") BoardDTO boardDTO, BindingResult bindingResult, HttpServletRequest request, ModelAndView mv) throws CommonException {
        Message message = new Message();
        HttpSession session = request.getSession();
        MemberSessionDTO member = (MemberSessionDTO) session.getAttribute("member"); //캐스캐이팅 조심
        Map<String, Object> resultMap = new HashMap<>();
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            message.setMessage(fieldError.getDefaultMessage());
            message.setStatus(StatusEnum.BAD_REQUEST);
            boardAddBlankCheck(boardDTO, message, fieldError);

            resultMap.put("message", message);
            mv.addObject("result", resultMap);
            mv.setViewName(noticeBoardAdd);
            log.debug("resultMap.toString={}", resultMap.toString());
            return mv;
        }
        boardService.boardSave(boardDTO, member.getEmail());
        mv.setViewName("redirect:/noticeBoard");
        return mv;
    }

    private void boardAddBlankCheck(BoardDTO boardDTO, Message message, FieldError fieldError) {
        if(boardDTO.getDetail()=="" && boardDTO.getTitle()==""){
            boardDTO.setDetail("title값을 넣어주세요");
            boardDTO.setTitle("detail값을 넣어 주세요");
        }
        else if(boardDTO.getDetail()=="" && boardDTO.getTitle()!="" ){
            boardDTO.setDetail(fieldError.getDefaultMessage());
        }
        else if( boardDTO.getDetail()!="" && boardDTO.getTitle()=="" ){
            boardDTO.setTitle(fieldError.getDefaultMessage());
        }
        message.setData(boardDTO);
    }

    @GetMapping("/{id}")
    public ModelAndView boardDetail(@PathVariable("id") long id, HttpServletRequest request,  ModelAndView mv) throws CommonException {
        Message message = new Message();
        Map<String, Object> resultMap = new HashMap<>();

        //최적화 필요 "필요 없는 데이터까지 가지고옴"
        BoardDetailVO boardDetailVO = boardService.getByIdBoard(id).orElseThrow(() -> new NoSuchElementException("Not Found Account"));
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle(boardDetailVO.getTitle());
        boardDTO.setDetail(boardDetailVO.getDetail());

        resultMap.put("id", id);
        resultMap.put("boardDTO", boardDTO);

        message.setData(resultMap);
//        mv.addObject("result", message);
        mv.addObject("id", id);
        mv.addObject("boardDTO", boardDTO);
        mv.setViewName(noticeBoard);
        return mv;
    }
    @GetMapping("/modify/{id}")
    public ModelAndView boardEdit(@PathVariable("id") long id, HttpServletRequest request,  ModelAndView mv) throws CommonException {
        log.debug("NoticeBoard.boardEdit()");
        HttpSession session = request.getSession();
        MemberSessionDTO member = (MemberSessionDTO) session.getAttribute("member");

        Map<String, Object>result = new HashMap<>();

        //최적화 필요 "필요 없는 데이터까지 가지고옴"
        BoardDetailVO boardDetailVO = boardService.getByIdBoard(id).orElseThrow(() -> new NoSuchElementException("Not Found Account"));
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle(boardDetailVO.getTitle());
        boardDTO.setDetail(boardDetailVO.getDetail());

        mv.addObject("boardDTO", boardDTO);
        mv.addObject("id",id);

        //비교해서 맞는 아이디면 수정으로 넘어가고 아니면 "인증되어지지 않는 유저입니다."라는 문구를 띄우자.
        if(!boardDetailVO.getMember().getEmail().equals(member.getEmail())){
            log.debug("mv={}", mv.toString());
            mv.setViewName(noticeBoard);
            return mv;
        }
        mv.setViewName(noticeBoardEdit);
            return mv;
    }

    @PostMapping("/modify/{id}")
    public ModelAndView boardEdit(@ModelAttribute("boardDTO") @Validated BoardDTO boardDTO, BindingResult bindingResult, @PathVariable("id") long id, HttpServletRequest request, ModelAndView mv) throws Exception {
        HttpSession session = request.getSession();
        MemberSessionDTO memberSessionDTO = (MemberSessionDTO) session.getAttribute("member");

        if(bindingResult.hasErrors()){
            mv.setViewName(noticeBoardEdit);
            return mv;
        }
        boardService.updateByBoard(id, boardDTO);
        mv.addObject("boardDTO", boardDTO);
        mv.setViewName(noticeBoardEdit);
        return mv;
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


    //공통처리할 bindingResult.hasErrors()를 처리할 방법을 생각해야함.

}
