package com.board.boardproject.service;

import com.board.boardproject.domain.Board;
import com.board.boardproject.domain.Comment;
import com.board.boardproject.repository.BoardMapper;
import com.board.boardproject.repository.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public boolean saveComment(Comment comment) {
        try {
            commentMapper.save(comment);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteComment(long commentId) {
        try {
            commentMapper.delete(commentId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Comment> findByBoardId(long boardId) {
        List<Comment> commentList;
        try {
            commentList = commentMapper.findByBoardId(boardId);
        } catch (Exception e) {
            return null;
        }
        return commentList;
    }
}
