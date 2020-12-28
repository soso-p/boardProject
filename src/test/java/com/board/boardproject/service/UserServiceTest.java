package com.board.boardproject.service;

import com.board.boardproject.domain.User;
import com.board.boardproject.repository.JdbcUserRepository;
import com.board.boardproject.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
// @Autowired를 사용하기 위해선 두 개의 어노테이션이 필요
@ExtendWith(SpringExtension.class)
@SpringBootTest // @SpringBootApplication을 찾아서 테스트를 위한 bean들을 다 생성
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    void findOne() {
        User user = new User();
        user.setId("test");
        user.setPassword("test1234");

        User user1 = userService.findOne(user.getId()).get();

        Assertions.assertThat(user1.getId()).isEqualTo(user.getId());
    }

    @Test
    void login() {
        User user = new User();
        user.setId("test");
        user.setPassword("test1234");

        boolean result = userService.login(user);
        Assertions.assertThat(result).isEqualTo(true);
    }
}