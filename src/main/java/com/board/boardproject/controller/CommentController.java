package com.board.boardproject.controller;

import com.board.boardproject.domain.Comment;
import com.board.boardproject.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
            /*
            comment.setBoardId(boardId);
            comment.setWriterId(session.getAttribute("loginId").toString());
            boolean result = commentService.saveComment(comment);
             */
            RestTemplate template = new RestTemplate();
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("content", comment.getContent());
            parameters.add("writerId", session.getAttribute("loginId").toString());
            try {
                ResponseEntity<HttpStatus> result = template.postForEntity("https://localhost:8081/comment2/"+boardId, parameters, HttpStatus.class);
                if (result.getBody().is4xxClientError()) {
                    return "redirect:/board/" + boardId;
                }
            } catch (Exception e) {
                return "redirect:/board/" + boardId;
            }
        }
        return "redirect:/board/"+boardId;
    }

    @DeleteMapping("/comment/{commentId}")
    public String deleteComment(@PathVariable("commentId") long commentId, HttpServletRequest request) {
        /*
        commentService.deleteComment(commentId);
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;

         */
        RestTemplate template = new RestTemplate();
        String referer = request.getHeader("Referer");
        try {
            template.delete("https://localhost:8081/comment2/" + commentId);
            return "redirect:" + referer;
        } catch (Exception e) {
            return "redirect:" + referer;
        }
    }
}
