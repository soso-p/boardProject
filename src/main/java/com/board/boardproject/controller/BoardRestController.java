package com.board.boardproject.controller;

import com.board.boardproject.domain.Board;
import com.board.boardproject.domain.Paging;
import com.board.boardproject.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class BoardRestController {
    @Autowired
    BoardService boardService;

    @GetMapping("/boardList2")
    public List<Board> boardList(@RequestParam(value = "nowPage", required = false) String nowPage, Model model) {

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


        model.addAttribute("boardList", boardService.findPage(paging));

        return boardService.findPage(paging);
    }

    // 게시글 등록
    @PostMapping("/board2")
    public ResponseEntity<HttpStatus> boardWrite(Board board) {
        boolean result = boardService.saveBoard(board);
        if (result)
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        else
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/board2/{boardId}")
    public HttpStatus boardModify(@PathVariable("boardId") long boardId, Board board) {
        board.setId(boardId);
        boolean result = boardService.modifyBoard(board);
        if (result) {
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    // 게시글 삭제
    @DeleteMapping("/board2/{boardId}")
    public HttpStatus boardDelete(@PathVariable("boardId") long boardId) {
        boolean result = boardService.deleteBoard(boardId);
        if (result) {
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    // 게시글 불러오기
    @GetMapping("/board2/{boardId}")
    public Board board(@PathVariable("boardId") long boardId) {
        return boardService.findById(boardId);
    }
}
