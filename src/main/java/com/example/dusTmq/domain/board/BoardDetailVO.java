package com.example.dusTmq.domain.board;

import com.example.dusTmq.domain.user.Member;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

//JPA의 롬복의 toString을 사용시 순환 참조가 생김으로 exclude로 순환참조 예방
@ToString(exclude = "createUserName")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
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
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    private Member member;
    @NotNull
    private String deleteUserName;

    @Builder
    public BoardDetailVO(String title, String detail, LocalDateTime createDate, LocalDateTime deleteDate, LocalDateTime updateDate, Member createUserName, String deleteUserName) {
        this.title = title;
        this.detail = detail;
        this.createDate = createDate;
        this.deleteDate = deleteDate;
        this.updateDate = updateDate;
        this.member = createUserName;
        this.deleteUserName = deleteUserName;
    }
    public void updateBoardDetail(String title, String detail, LocalDateTime updateDate,  Member updateUserName){
            this.title=title;
            this.detail=detail;
            this.updateDate=updateDate;
            this.member=updateUserName;
    }
}
