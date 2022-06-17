package com.example.dusTmq.controller;

import com.example.dusTmq.common.Message;
import com.example.dusTmq.common.StatusEnum;
import com.example.dusTmq.common.exception.MemberException;
import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.user.Role;
import com.example.dusTmq.domain.user.dto.MemberRegisterDTO;
import com.example.dusTmq.service.Board.IBoard;
import com.example.dusTmq.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class mainController {

    private final IBoard boardService;
    private final MemberService memberService;
    private final BCryptPasswordEncoder encoder;

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

    @GetMapping("/register")
    public ModelAndView memberRegister(){
        ModelAndView mv = new ModelAndView();
        MemberRegisterDTO memberRegisterDTO = new MemberRegisterDTO();
        mv.addObject("memberRegisterDTO", memberRegisterDTO);
        mv.setViewName("/login/register");
        return mv;
    }
    @PostMapping("/register")
    public ModelAndView memberRegister(@Validated @ModelAttribute MemberRegisterDTO memberRegisterDTO, BindingResult bindingResult) throws MemberException {
        log.debug("MemberRegisterDTO={}", memberRegisterDTO);
        ModelAndView mv = new ModelAndView();
        Member member = Member.builder()
                .role(Role.ROLE_USER)
                .email(memberRegisterDTO.getEmail())
                .pwd(encoder.encode(memberRegisterDTO.getPwd()))
                .lastLoginTime(LocalDateTime.now())
                .build();
        memberService.saveMember(member);

        mv.setViewName("redirect:/login?register_Success");
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





}
