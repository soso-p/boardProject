package com.board.boardproject.repository;

import com.board.boardproject.domain.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcBoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void save() {
        // given
        Board board = new Board();
        board.setWriterId("test");
        board.setTitle("테스트 제목");
        board.setContent("테스트 내용");

        // when
        Board board1 = boardRepository.save(board).get();

        // then
        Assertions.assertThat(board1.getTitle()).isEqualTo(board.getTitle());
    }

    @Test
    void delete() {
        Board board = new Board();
        board.setWriterId("test");
        board.setTitle("테스트 제목");
        board.setContent("테스트 내용");
        boardRepository.save(board);
        Board board1 = boardRepository.findByTitle(board.getTitle()).get();

        boolean result = boardRepository.delete(board1.getId());

        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    void modify() {
        Board board = new Board();
        board.setWriterId("test");
        board.setTitle("테스트 제목");
        board.setContent("테스트 내용");
        boardRepository.save(board);

        Board board2 = boardRepository.findByTitle(board.getTitle()).get();
        board2.setContent("테스트 내용 수정");

        boolean result = boardRepository.modify(board);

        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    void findAll() {

    }

    @Test
    void findPage() {
        List<Board> boardList = boardRepository.findPage(0, 6);

        Assertions.assertThat(boardList.size()).isEqualTo(6);
    }
}