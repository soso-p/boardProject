package com.board.boardproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {
    @GetMapping("board")
    public String board(@RequestParam(value = "user", required = false) String user, Model model) {
        model.addAttribute("user", user);
        return "board";
    }

    @GetMapping("")
    public String index(@RequestParam(value = "user", required = false) String user, Model model) {
        model.addAttribute("user", user);
        return "index";
    }
}
