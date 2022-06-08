package com.example.dusTmq.utill;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
public class UtilConfig {
    @Value("${javascript_nonce}")
    private String nonce;
}
