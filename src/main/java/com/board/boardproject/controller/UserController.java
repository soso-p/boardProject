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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    public String join(User user, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
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
        if (user.getId().equals("") || user.getPassword().equals("")) { // id 혹은 password를 입력을 안 한 경우
            String referer = request.getHeader("Referer");
            redirectAttributes.addFlashAttribute("error", "id, password를 다 입력해주세요.");
            return "redirect:" + referer;
        }
        else { // 둘 다 입력한 경우
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
    }

    @GetMapping("/logout")
    public String logout(User user, Model model, HttpSession session) {
        session.invalidate();
        return "index";
    }
}
