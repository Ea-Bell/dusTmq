package com.example.dusTmq.domain.comment;

import com.example.dusTmq.domain.board.BoardDetailVO;
import com.example.dusTmq.domain.user.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("ParentComment")
@PrimaryKeyJoinColumn(name = "parent_comment_id")
public class ParentComment extends Comment{

    private String content;

    @Builder(builderMethodName = "newComment")
    public ParentComment( BoardDetailVO boardDetailVO, Member member, String content) {
        super( boardDetailVO, member);
        this.content=content;
    }
}
