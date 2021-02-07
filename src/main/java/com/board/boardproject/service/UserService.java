package com.board.boardproject.service;

import com.board.boardproject.domain.User;
import com.board.boardproject.repository.JdbcUserRepository;
import com.board.boardproject.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private UserMapper userMapper;

    @Autowired
    UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /*
    //@Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

     */

    public boolean join(User user) {
        try {
            insectDuplicateUser(user);
        } catch (IllegalStateException e) {
            return false;
        }
        userMapper.save(user);
        return true;
    }

    public boolean login(User user) {
        Optional<User> result = userMapper.findById(user.getId());
        if (result.isEmpty()) {
            return false;
        }
        if ((result.get().getId().equals(user.getId())) && (result.get().getPassword().equals(user.getPassword()))) {
            return true;
        }
        else {
            return false;
        }
    }

    private void insectDuplicateUser(User user) {
        userMapper.findById(user.getId()).ifPresent(u -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    public Optional<User> findOne(String userId) {
        return userMapper.findById(userId);
    }
}
