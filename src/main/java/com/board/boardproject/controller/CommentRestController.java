package com.board.boardproject.controller;

import com.board.boardproject.domain.Comment;
import com.board.boardproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommentRestController {
    @Autowired
    CommentService commentService;

    @GetMapping("/board/{boardId}/comment")
    public List<Comment> commentList(@PathVariable("boardId") long boardId) {
        return commentService.findByBoardId(boardId);
    }

    @PostMapping("/comment2/{boardId}")
    public HttpStatus createComment(@PathVariable("boardId") long boardId, Comment comment) {
        comment.setBoardId(boardId);
        boolean result = commentService.saveComment(comment);
        if (result) {
            return HttpStatus.CREATED;
        }
        else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping("/comment2/{commentId}")
    public HttpStatus deleteComment(@PathVariable("commentId") long commentId) {
        boolean result = commentService.deleteComment(commentId);
        if (result) {
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
