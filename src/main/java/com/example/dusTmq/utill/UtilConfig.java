package com.example.dusTmq.utill;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
public class UtilConfig {
    @Value("${nonce}")
    private String nonce;
}
