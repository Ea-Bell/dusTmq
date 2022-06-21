package com.example.dusTmq.domain.comment;

import com.example.dusTmq.domain.board.BoardDetailVO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy =InheritanceType.JOINED)
@ToString(exclude = {"boardDetailVO", "member", "parent", "childComment"})
//컴포지션 클래스
public abstract class Comment extends BaseTImeEntity {

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

    public Comment(BoardDetailVO boardDetailVO, Member member) {
        this.boardDetailVO = boardDetailVO;
        this.member = member;
    }
}


