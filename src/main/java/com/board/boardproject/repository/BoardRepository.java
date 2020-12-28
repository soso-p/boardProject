package com.board.boardproject.repository;

import com.board.boardproject.domain.Board;
import com.board.boardproject.domain.Paging;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Optional<Board> save(Board board);
    Boolean delete(long id);
    Boolean modify(Board board);
    List<Board> findAll();
    List<Board> findPage(int start, int end);
    Optional<Board> findByTitle(String title);
    int findAllCount();
}
