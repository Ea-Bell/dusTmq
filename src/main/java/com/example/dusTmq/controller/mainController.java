package com.example.dusTmq.controller;

import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import com.example.dusTmq.service.Board.IBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class mainController {

    private final IBoard boardService;
    private String path = "/noticeBoard";


    @GetMapping("/index")
    public ModelAndView index (){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/index");
        return mv;
    }

    @GetMapping("/noticeBoard")
    public ModelAndView tables(@PageableDefault(size = 100000, sort = "id", direction = Sort.Direction.DESC ) Pageable pageable){
        ModelAndView mv = new ModelAndView();
        Page<BoardListDTO> boardListDTOS = boardService.pagingBoardListBy(pageable);
        mv.addObject("boardListDTOS", boardListDTOS);
        mv.setViewName("/noticeBoard/noticeBoardList");
        return mv;
    }
    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/login/login");
        return mv;
    }
    
    // 빈템플릿 파일 테스트 코드
    @GetMapping("/blank")
    public ModelAndView blank(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/common/blank");
        return mv;
    }
}
