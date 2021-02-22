package com.board.boardproject.repository;

import com.board.boardproject.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    void save(@Param("comment") Comment comment);
    void delete(@Param("id") long id);
    List<Comment> findAll();
    List<Comment> findByBoardId(@Param("boardId") long boardId);
    Comment findById(@Param("id") long id);
}
