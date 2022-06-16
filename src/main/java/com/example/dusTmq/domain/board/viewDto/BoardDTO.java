package com.example.dusTmq.domain.board.viewDto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class BoardDTO {
    @NotBlank(message = "title값을 넣어주세요")
    private String title;
    @NotBlank(message = "detail값을 넣어 주세요")
    private String detail;

    public BoardDTO() {
        this.title = "";
        this.detail = "";
    }
}
