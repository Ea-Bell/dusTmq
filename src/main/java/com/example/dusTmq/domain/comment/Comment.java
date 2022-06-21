package com.example.dusTmq.domain.comment;

import com.example.dusTmq.domain.board.BoardDetailVO;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.util.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment  extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private int depth; // 깊이


    //부모, 자식에 대한 comment가 필요하다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_detail_id")
    @Column(nullable = false)
    private BoardDetailVO boardDetailVO;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Column(nullable = false)
    private Member member;

    @Builder(builderMethodName = "newComment")
    public Comment(String content, int depth, BoardDetailVO boardDetailVO, Member member) {
        this.content = content;
        this.depth = depth;
        this.boardDetailVO = boardDetailVO;
        this.member = member;
    }
}
