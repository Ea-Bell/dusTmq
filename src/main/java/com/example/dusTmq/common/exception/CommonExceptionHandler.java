package com.example.dusTmq.common.exception;

import com.example.dusTmq.domain.board.viewDto.BoardDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

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

//    @ExceptionHandler(AccessDeniedException.class)
//    public ErrorResult badRequestError(Exception exception) {
//
//        log.debug("CommonExceptionHandler.badRequestError()");
//        ErrorResult errorResult = new ErrorResult();
//        errorResult.setStatus(HttpStatus.BAD_REQUEST);
//        errorResult.setMessage(exception.getMessage());
//        return errorResult;
//    }
}
