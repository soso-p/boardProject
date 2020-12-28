package com.board.boardproject.controller;

import com.board.boardproject.domain.Paging;
import com.board.boardproject.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/boardList")
    public String boardList(@RequestParam(value = "nowPage", required = false) String nowPage, Model model) {
        int total = boardService.getAllBoardCount();
        Paging paging;
        if (nowPage != null) {
            paging = new Paging(total, Integer.parseInt(nowPage), 10);
            if (paging.getNowPage() > paging.getLastPage()) {
                paging.setNowPage(paging.getLastPage());
            }
        }
        else {
            paging = new Paging(total, 1, 10);
        }

        model.addAttribute("paging", paging);

        model.addAttribute("boardList", boardService.findPage(paging));

        return "boardList";
    }

    @GetMapping("/board")
    public String board(@RequestParam(value = "boardId") long boardId, Model model) {

        return "board";
    }
}
