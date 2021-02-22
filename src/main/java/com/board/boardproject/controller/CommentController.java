package com.board.boardproject.controller;

import com.board.boardproject.domain.Comment;
import com.board.boardproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/comment/{boardId}")
    public String createComment(@PathVariable("boardId") long boardId, Comment comment, HttpSession session) {
        if (!comment.getContent().equals("")) {
            comment.setBoardId(boardId);
            comment.setWriterId(session.getAttribute("loginId").toString());
            boolean result = commentService.saveComment(comment);
        }
        return "redirect:/board/"+boardId;
    }

    @DeleteMapping("/comment/{commentId}")
    public String deleteComment(@PathVariable("commentId") long commentId, HttpServletRequest request) {
        commentService.deleteComment(commentId);
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }
}
