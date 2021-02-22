package com.board.boardproject.repository;

import com.board.boardproject.domain.Board;
import com.board.boardproject.domain.Comment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CommentMapperTest {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    BoardMapper boardMapper;

    @Test
    void save() {
        // given
        Board board = new Board();
        board.setWriterId("test");
        board.setTitle("테스트 중!!!");
        board.setContent("테스트 내용");
        boardMapper.save(board);

        Comment comment = new Comment();
        comment.setBoardId(board.getId());
        comment.setWriterId("test");
        comment.setContent("테스트중입니다.");

        // when
        commentMapper.save(comment);

        // then
        Assertions.assertThat(comment.getId()).isNotNull();
    }

    @Test
    void delete() {
        Board board = new Board();
        board.setWriterId("test");
        board.setTitle("테스트 중!!!");
        board.setContent("테스트 내용");
        boardMapper.save(board);

        Comment comment = new Comment();
        comment.setBoardId(board.getId());
        comment.setWriterId("test");
        comment.setContent("테스트중입니다.");
        commentMapper.save(comment);

        commentMapper.delete(comment.getId());
        Comment comment2 = commentMapper.findById(comment.getId());

        Assertions.assertThat(comment2 == null).isEqualTo(true);
    }

    @Test
    void findAll() {

    }

    @Test
    void findByBoardId() {
        // given
        Board board = new Board();
        board.setWriterId("test");
        board.setTitle("테스트 중!!!");
        board.setContent("테스트 내용");
        boardMapper.save(board);

        Comment comment = new Comment();
        comment.setBoardId(board.getId());
        comment.setWriterId("test");
        comment.setContent("테스트중입니다.");

        // when
        commentMapper.save(comment);
        List<Comment> commentList = commentMapper.findByBoardId(board.getId());

        Assertions.assertThat(commentList.size()).isEqualTo(1);
    }
}