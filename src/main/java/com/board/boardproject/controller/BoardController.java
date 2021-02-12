package com.board.boardproject.controller;

import com.board.boardproject.domain.Board;
import com.board.boardproject.domain.Paging;
import com.board.boardproject.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

        /*
        model.addAttribute("paging", paging);

        model.addAttribute("boardList", boardService.findPage(paging));

        return "boardList";
         */
        RestTemplate template = new RestTemplate();
        List<Board> boardList = template.getForObject("http://localhost:8081/boardList2?nowPage="+paging.getNowPage(), List.class);
        model.addAttribute("paging", paging);
        model.addAttribute("boardList", boardList);

        return "boardList";
    }

    @GetMapping("/board/{boardId}")
    public String board(@PathVariable("boardId") long boardId, Model model) {
        Board board = boardService.findById(boardId);
        model.addAttribute("boardId", boardId);
        model.addAttribute("board", board);
        return "board";
    }

    // 게시글 삭제
    @DeleteMapping("/board/{boardId}")
    public String boardDelete(@PathVariable("boardId") long boardId) {
        boolean result = boardService.deleteBoard(boardId);
        return "redirect:/boardList";
    }

    // 게시글 작성 폼 반환
    @GetMapping("boardWrite")
    public String boardWriteForm() {
        return "boardWrite";
    }

    // 게시글 등록
    @PostMapping("/board")
    public String boardWrite(Model model, Board board) {
        if (board.getTitle().equals("")|| board.getContent().equals("")) {
            model.addAttribute("error", "제목과 내용을 작성해주세요.");
            model.addAttribute("board", board);
            return "boardWrite";
        }
        else {
            boardService.saveBoard(board);
            return "redirect:/boardList";
        }
    }

    // 게시글 수정 폼 반환
    @GetMapping("boardModify")
    public String boardModifyForm(@RequestParam(value = "boardId") long boardId, Model model) {
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);
        return "boardModifyForm";
    }

    // 게시글 수정
    @PutMapping("/board/{boardId}")
    public String boardModify(@PathVariable("boardId") long boardId, Board board) {
        board.setId(boardId);
        boardService.modifyBoard(board);
        return "redirect:/boardList";
    }
}
