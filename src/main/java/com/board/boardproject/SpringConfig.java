package com.board.boardproject;

import com.board.boardproject.repository.BoardRepository;
import com.board.boardproject.repository.JdbcBoardRepository;
import com.board.boardproject.repository.JdbcUserRepository;
import com.board.boardproject.repository.UserRepository;
import com.board.boardproject.service.BoardService;
import com.board.boardproject.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new JdbcUserRepository(dataSource);
    }

    @Bean
    public BoardRepository boardRepository() {
        return new JdbcBoardRepository(dataSource);
    }

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository());
    }
}
