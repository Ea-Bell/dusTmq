package com.example.dusTmq.domain.board;

import com.example.dusTmq.domain.user.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@ToString
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDetailVO {

    @Id
    @Column(name = "boardDetail_Id")
    @GeneratedValue
    private Long id;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    private String detail;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleteDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
    @NotNull
    private String createUserName;
    @NotNull
    private String deleteUserName;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;


    @Builder
    public BoardDetailVO(String title, String detail, LocalDateTime createDate, LocalDateTime deleteDate, LocalDateTime updateDate, String createUserName, String deleteUserName) {
        this.title = title;
        this.detail = detail;
        this.createDate = createDate;
        this.deleteDate = deleteDate;
        this.updateDate = updateDate;
        this.createUserName = createUserName;
        this.deleteUserName = deleteUserName;
    }

    public BoardDetailVO() {

    }

    public void updateBoardDetail(String title, String detail, LocalDateTime updateDate,  String updateUserName){
            this.title=title;
            this.detail=detail;
            this.updateDate=updateDate;
            this.createUserName=updateUserName;
    }

}
