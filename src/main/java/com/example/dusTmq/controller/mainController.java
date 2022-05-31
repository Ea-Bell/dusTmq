package com.example.dusTmq.controller;

import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import com.example.dusTmq.service.Board.IBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class mainController {

    private final IBoard boardService;
    private String path = "/noticeBoard";


//    @GetMapping("/index")
//    public ModelAndView index (){
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("/index");
//        return mv;
//    }

    @GetMapping("/noticeBoard")
    public ModelAndView tables(@PageableDefault(size = 100000, sort = "id", direction = Sort.Direction.DESC ) Pageable pageable){
        ModelAndView mv = new ModelAndView();
        Page<BoardListDTO> boardListDTOS = boardService.pagingBoardListBy(pageable);
        mv.addObject("boardListDTOS", boardListDTOS);
        mv.setViewName("/noticeBoard/noticeBoardList");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception
            ) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("error", error);
        mv.addObject("exception", exception);
        mv.setViewName("/login/login");
        return mv;
    }

//    @PostMapping("/login")
//    public ModelAndView login(@RequestParam Map<String, Object>result) {
//        ModelAndView mv = new ModelAndView();
//
//        log.debug("username={}, password={}", result.toString(), result.toString());
//        mv.setViewName("redirect:/index");
//        return mv;
//    }
    @GetMapping("/register")
    public ModelAndView memberRegister(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/login/register");
        return mv;
    }
    
    // 빈템플릿 파일 테스트 코드
    @GetMapping("/blank")
    public ModelAndView blank(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/common/blank");
        return mv;
    }
    @GetMapping("/admin")
    public ModelAndView admin(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/home");
        return  mv;
    }
    @GetMapping("/user")
    public ModelAndView user(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/user/userInfo");
        return mv;
    }
}
