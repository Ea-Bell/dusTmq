package com.example.dusTmq.common;

import com.example.dusTmq.domain.board.viewDto.BoardListDTO;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

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
}

