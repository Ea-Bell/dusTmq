package com.example.dusTmq.repository.board.comment;

import com.example.dusTmq.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> , ICommentRepository{
}