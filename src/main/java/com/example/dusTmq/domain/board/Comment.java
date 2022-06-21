package com.example.dusTmq.domain.board;

import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.util.BaseTImeEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"boardDetailVO", "member"})
public class Comment extends BaseTImeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = BoardDetailVO.class)
    @JoinColumn(name = "boardDetail_Id")
    @NotNull
    private BoardDetailVO boardDetailVO;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @NotNull
    private String content;

    //parentCommentId가 자기 자신을 가리키면 최상위 코멘트이다.
  
    private Long parentCommentId;

    @Builder(builderMethodName = "newComment")
    public Comment( BoardDetailVO boardDetailVO, Member member, String contents) {
        this.commentId = commentId;
        this.boardDetailVO = boardDetailVO;
        this.member = member;
        this.content = contents;
    }

    @Builder(builderMethodName = "nestedComments")
    public Comment(BoardDetailVO boardDetailVO, Member member, String content, Long parentCommentId) {
        this.boardDetailVO = boardDetailVO;
        this.member = member;
        this.content = content;
        this.parentCommentId = parentCommentId;
    }

    public void newParentCommentId(Long parentCommentId){
         this.parentCommentId = parentCommentId;
    }



}


