package com.board.boardproject.repository;

import com.board.boardproject.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository {
    Optional<Comment> save(Comment comment);
    Boolean delete(long id);
    List<Comment> findAll();
    List<Comment> findByBoardId(long boardId);
}
