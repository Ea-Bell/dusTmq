package com.example.dusTmq.common.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResult {
    private HttpStatus status;
    private String message;
}
