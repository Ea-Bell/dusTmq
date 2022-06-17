package com.example.dusTmq.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class userController {

    @GetMapping
    public ModelAndView user(ModelAndView mv){
        mv.setViewName("/user/userInfo");
        return mv;
    }
    @GetMapping("/userInfoDetail")
    public ModelAndView getUserInfo(ModelAndView mv){
        mv.setViewName("/user/userInfoDetail");
        return mv;
    }

}
