package com.board.boardproject.controller;

import com.board.boardproject.domain.User;
import com.board.boardproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserRestController {
    @Autowired
    UserService userService;

    @PostMapping("/loginUser2")
    public HttpStatus login(User user) {
        boolean result = userService.login(user);
        if (result) {
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping("/joinUser2")
    public HttpStatus join(User user) {
        boolean result = userService.join(user);
        if (result) {
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
