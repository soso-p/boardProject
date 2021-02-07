package com.board.boardproject.service;

import com.board.boardproject.domain.Board;
import com.board.boardproject.domain.Paging;
import com.board.boardproject.repository.BoardMapper;
import com.board.boardproject.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BoardService {
    private BoardMapper boardMapper;

    @Autowired
    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public boolean saveBoard(Board board) {
        try {
            boardMapper.save(board);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteBoard(long boardId) {
        try {
            boardMapper.delete(boardId);
        } catch (Exception e) {
            return false;
        }
        return true;
        /*
        boolean result = boardMapper.delete(boardId);
        if (result) {
            return true;
        }
        else {
            return false;
        }

         */
    }

    public boolean modifyBoard(Board board) {
        try {
            boardMapper.modify(board);
        } catch (Exception e) {
            return false;
        }
        return true;
        /*
        boolean result = boardMapper.modify(board);
        if (result) {
            return true;
        }
        else {
            return false;
        }

         */
    }

    public Board findById(long boardId) {
        Board board = boardMapper.findById(boardId).get();
        //board.setContent(board.getContent().replaceAll("\n", "<br>"));
        return board;
    }

    public int getAllBoardCount() {
        return boardMapper.findAllCount();
    }

    public List<Board> findPage(Paging paging) {
        return boardMapper.findPage(paging.getStart(), paging.getEnd());
    }

}
