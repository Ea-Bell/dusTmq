package com.example.dusTmq.controller.login;

import com.example.dusTmq.domain.user.dto.MemberDTO;
import com.example.dusTmq.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class loginController {

    private final MemberService memberService;


    @PostMapping("/memberAdd")
    public ModelAndView ModelAndView(@Validated @ModelAttribute("memberDTO") MemberDTO memberDTO, BindingResult bindingResult){
        ModelAndView mv =new ModelAndView();

        mv.addObject("memberDTO", memberDTO);
        return mv;
    }

}
