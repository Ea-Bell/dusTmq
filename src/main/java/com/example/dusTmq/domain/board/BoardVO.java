package com.example.dusTmq.domain.board;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
public class BoardVO {
    @Id
    @GeneratedValue
    private Long id;


    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardDetail_Id")
    private BoardDetailVO boardDetailVO;

    @NotBlank
    private String title;
    @NotBlank
    private LocalDateTime createDate;
    @NotNull
    private LocalDateTime deleteDate;
    @NotNull
    private String createUserName;
    @NotNull
    private String deleteUserName;

    protected BoardVO() {
    }

    @Builder
    public BoardVO(BoardDetailVO boardDetailVO, String title, LocalDateTime createDate, LocalDateTime deleteDate, String createUserName, String deleteUserName) {
        this.boardDetailVO = boardDetailVO;
        this.title = title;
        this.createDate = createDate;
        this.deleteDate = deleteDate;
        this.createUserName = createUserName;
        this.deleteUserName = deleteUserName;
    }
}
