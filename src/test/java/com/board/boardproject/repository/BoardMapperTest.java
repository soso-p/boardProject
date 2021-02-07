package com.board.boardproject.repository;

import com.board.boardproject.domain.Board;
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
class BoardMapperTest {
    @Autowired
    BoardMapper boardMapper;

    @Test
    void save() {
        // given
        Board board = new Board();
        board.setWriterId("test");
        board.setTitle("테스트 중!!!");
        board.setContent("테스트 내용");

        // when
        boardMapper.save(board);

        // then
        Assertions.assertThat(board.getId()).isNotNull();
    }

    @Test
    void delete() {
        Board board = new Board();
        board.setWriterId("test");
        board.setTitle("테스트 제목");
        board.setContent("테스트 내용");
        boardMapper.save(board);

        boardMapper.delete(board.getId());
        Optional<Board> board2 = boardMapper.findById(board.getId());

        Assertions.assertThat(board2.isEmpty()).isEqualTo(true);
    }

    @Test
    void modify() {
        Board board = new Board();
        board.setWriterId("test");
        board.setTitle("테스트 제목");
        board.setContent("테스트 내용");
        boardMapper.save(board);

        board.setContent("테스트 내용 수정");

        boardMapper.modify(board);
        Board board3 = boardMapper.findById(board.getId()).get();

        Assertions.assertThat(board3.getContent()).isEqualTo(board.getContent());
    }

    @Test
    void findAll() {

    }

    @Test
    void findPage() {
        List<Board> boardList = boardMapper.findPage(0, 6);

        Assertions.assertThat(boardList.size()).isEqualTo(6);
    }
}