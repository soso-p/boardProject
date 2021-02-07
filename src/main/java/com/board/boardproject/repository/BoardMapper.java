package com.board.boardproject.repository;

import com.board.boardproject.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface BoardMapper {
    void save(@Param("board") Board board);
    void delete(@Param("id") long id);
    void modify(@Param("board") Board board);
    List<Board> findAll();
    List<Board> findPage(@Param("start") int start, @Param("end") int end);
    Optional<Board> findByTitle(@Param("title") String title);
    int findAllCount();
    Optional<Board> findById(@Param("boardId") long boardId);
}
