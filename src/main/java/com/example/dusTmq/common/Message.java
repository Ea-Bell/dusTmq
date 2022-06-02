package com.example.dusTmq.common;

import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import lombok.Data;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;

@Data
public class Message {
    private StatusEnum status;
    private String message;
    private Object data;


    public Message() {
        this.status= StatusEnum.BAD_REQUEST;
        this.data= null;
        this.message= null;

    }

    public static Message getMessage(BindingResult bindingResult, Logger log) {
        Message errorMsg = new Message();
        FieldError fieldError = bindingResult.getFieldError();

        errorMsg.setStatus(StatusEnum.BAD_REQUEST);
        errorMsg.setMessage(fieldError.getDefaultMessage());
        errorMsg.setData(fieldError.getField());

        log.error("errorMsg={}", errorMsg);
        return errorMsg;
    }
}

