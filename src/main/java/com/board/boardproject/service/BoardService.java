package com.board.boardproject.service;

import com.board.boardproject.domain.Board;
import com.board.boardproject.domain.Paging;
import com.board.boardproject.repository.BoardRepository;
import com.board.boardproject.repository.JdbcBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BoardService {
    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public boolean saveBoard(Board board) {
        try {
            boardRepository.save(board);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteBoard(long boardId) {
        boolean result = boardRepository.delete(boardId);
        if (result) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean modifyBoard(Board board) {
        boolean result = boardRepository.modify(board);
        if (result) {
            return true;
        }
        else {
            return false;
        }
    }

    public Board findById(long boardId) {
        Board board = boardRepository.findById(boardId).get();
        //board.setContent(board.getContent().replaceAll("\n", "<br>"));
        return board;
    }

    public int getAllBoardCount() {
        return boardRepository.findAllCount();
    }

    public List<Board> findPage(Paging paging) {
        return boardRepository.findPage(paging.getStart(), paging.getEnd());
    }

}
