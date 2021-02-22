package com.board.boardproject.controller;

import com.board.boardproject.domain.Comment;
import com.board.boardproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentRestController {
    @Autowired
    CommentService commentService;

    @GetMapping("/board/{boardId}/comment")
    public List<Comment> commentList(@PathVariable("boardId") long boardId) {
        return commentService.findByBoardId(boardId);
    }
}
