package com.board.boardproject.controller;


import com.board.boardproject.domain.User;
import com.board.boardproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/users/new")
    public String joinUser() { return "joinUser"; }

    @PostMapping("/loginUser")
    public String login(User user, Model model, HttpSession session) {
        /*
        boolean result = userService.login(user);
        if (result) {
            session.setAttribute("loginId", user.getId());
            return "index";
        }
        else {
            model.addAttribute("error", "ID나 PW가 틀립니다.");
            return "login";
        }

         */
        RestTemplate template = new RestTemplate();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("id", user.getId());
        parameters.add("password", user.getPassword());
        try {
            ResponseEntity<HttpStatus> result = template.postForEntity("https://localhost:8081/loginUser2", parameters, HttpStatus.class);
            if (result.getBody().is4xxClientError()) {
                model.addAttribute("error", "ID나 PW가 틀립니다.");
                return "login";
            }
            session.setAttribute("loginId", user.getId());
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", "ID나 PW가 틀립니다.");
            return "login";
        }
    }

    @PostMapping("/joinUser")
    public String join(User user, Model model) {
        /*
        boolean result = userService.join(user);
        if (result) {
            return "login";
        }
        else {
            model.addAttribute("message", "같은 아이디가 있습니다.");
            return "joinUser";
        }

         */

        RestTemplate template = new RestTemplate();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("id", user.getId());
        parameters.add("password", user.getPassword());
        try {
            ResponseEntity<HttpStatus> result = template.postForEntity("https://localhost:8081/joinUser2", parameters, HttpStatus.class);
            if (result.getBody().is4xxClientError()) {
                model.addAttribute("message", "같은 아이디가 있습니다.");
                return "joinUser";
            }
            return "login";
        } catch (Exception e) {
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
