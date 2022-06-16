package com.example.dusTmq.common.exception;

import com.example.dusTmq.common.Message;
import com.example.dusTmq.common.StatusEnum;
import com.example.dusTmq.domain.board.viewDto.BoardDTO;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    public final static String REDIRECT_ERROR400 = "redirect:/error-400";
    public final static String REDIRECT_ERROR404 = "redirect:/error-404";
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CommonException.class)
    public String intervalServerError(CommonException commonException){
        log.debug("CommonExceptionHandler.intervalServerError()");
        BoardDTO boardDTO = new BoardDTO();
        ErrorResult errorResult= new ErrorResult();
        errorResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResult.setMessage(commonException.getMessage());
        log.error("exceptionHandler ex={}",errorResult);
//        Model mv = null;
//        mv.addAttribute("errorResult", errorResult);
//        mv.addAttribute("boardDTO", boardDTO);
        Model mv = null;

        mv.addAttribute("boardDTO", boardDTO);
        
        return "/noticeBoard/noticeBoardEdit";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String noticeBoardError(NoSuchElementException error){
        return "redirect:/error-400";
    }

}
