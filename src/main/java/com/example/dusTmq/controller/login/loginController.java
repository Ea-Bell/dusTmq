package com.example.dusTmq.controller.login;

import com.example.dusTmq.common.Message;
import com.example.dusTmq.common.StatusEnum;
import com.example.dusTmq.domain.user.dto.MemberDTO;
import com.example.dusTmq.domain.user.dto.MemberLoginDTO;
import com.example.dusTmq.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
public class loginController {

    private final MemberService memberService;


    @PostMapping("/register")
    public ModelAndView memberRegister(@Validated @ModelAttribute("MemberLoginDTO") MemberDTO memberDTO, BindingResult bindingResult){

        System.out.println("memberDTO = " + memberDTO);
        ModelAndView mv =new ModelAndView();

        mv.addObject("memberDTO", memberDTO);
        return mv;
    }

    @GetMapping("/register")
    public ModelAndView memberRegister(){
        MemberDTO memberDTO = new MemberDTO();
        ModelAndView mv = new ModelAndView();

        mv.setViewName("login/register");
        mv.addObject("memberDTO", memberDTO);

        return mv;
    }



    private Message memberError(BindingResult bindingResult) {
        FieldError fieldError = bindingResult.getFieldError();
        Message errorMsg = new Message();

        errorMsg.setStatus(StatusEnum.BAD_REQUEST);
        errorMsg.setMessage(fieldError.getDefaultMessage());
        errorMsg.setData(fieldError.getField());

        log.error("errorMsg={}", errorMsg);

        return errorMsg;
    }

}
