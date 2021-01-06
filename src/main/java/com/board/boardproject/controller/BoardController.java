package com.board.boardproject.controller;

import com.board.boardproject.domain.Board;
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

    @GetMapping("/board/{boardId}")
    public String board(@PathVariable("boardId") long boardId, Model model) {
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);
        return "board";
    }

    @GetMapping("/boardDelete")
    public String boardDelete(@RequestParam(value = "boardId") long boardId) {
        boolean result = boardService.deleteBoard(boardId);
        return "redirect:boardList";
    }

    @GetMapping("/boardWrite")
    public String boardWrite() {
        return "boardWrite";
    }

    @PostMapping("/boardCreate")
    public String boardCreate(Board board) {
        boardService.saveBoard(board);
        return "redirect:boardList";
    }

    @GetMapping("/boardModify")
    public String boardModify(@RequestParam(value = "boardId") long boardId, Model model) {
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);
        return "boardModifyForm";
    }

    @PostMapping("/boardUpdate")
    public String boardUpdate(@RequestParam(value = "boardId") long boardId, Board board) {
        board.setId(boardId);
        boardService.modifyBoard(board);
        return "redirect:boardList";
    }
}
