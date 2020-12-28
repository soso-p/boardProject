package com.board.boardproject.controller;


import com.board.boardproject.domain.User;
import com.board.boardproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/users/new")
    public String joinUser() { return "joinUser"; }

    @PostMapping("/loginUser")
    public String login(User user, Model model, HttpSession session) {
        boolean result = userService.login(user);
        if (result) {
            session.setAttribute("loginId", user.getId());
            return "index";
        }
        else {
            model.addAttribute("error", "ID나 PW가 틀립니다.");
            return "login";
        }
    }

    @PostMapping("/joinUser")
    public String join(User user, Model model) {
        boolean result = userService.join(user);
        if (result) {
            return "login";
        }
        else {
            model.addAttribute("message", "같은 아이디가 있습니다.");
            return "joinUser";
        }
    }

    @GetMapping("/logout")
    public String logout(User user, Model model, HttpSession session) {
        session.invalidate();
        return "index";
    }
}
