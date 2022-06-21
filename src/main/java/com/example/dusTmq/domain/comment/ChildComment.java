package com.example.dusTmq.domain.comment;

import com.example.dusTmq.domain.board.BoardDetailVO;
import com.example.dusTmq.domain.user.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("childComment")
@PrimaryKeyJoinColumn(name = "child_comment_id")
public class ChildComment extends Comment{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private ParentComment parent;


    private String content;


    @Builder(builderMethodName = "newChildComment")
    public ChildComment( BoardDetailVO boardDetailVO, Member member, String content, ParentComment parent) {
        super( boardDetailVO, member);
        this.parent = parent;
        this.content= content;

    }
}
