package com.board.boardproject.repository;

import com.board.boardproject.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    void save() {
        User user = new User();
        user.setId("usertest");
        user.setPassword("usertest1234");

        userMapper.save(user);
        Optional<User> user1 = userMapper.findById("usertest");

        Assertions.assertThat(user1.isPresent()).isEqualTo(true);
    }

    @Test
    void findById() {
        User user = new User();
        user.setId("usertest");
        user.setPassword("usertest1234");

        userMapper.save(user);
        Optional<User> user1 = userMapper.findById("usertest");

        Assertions.assertThat(user1.get().getId()).isEqualTo("usertest");
    }
}