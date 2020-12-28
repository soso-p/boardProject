package com.board.boardproject.service;

import com.board.boardproject.domain.User;
import com.board.boardproject.repository.JdbcUserRepository;
import com.board.boardproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean join(User user) {
        try {
            insectDuplicateUser(user);
        } catch (IllegalStateException e) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public boolean login(User user) {
        Optional<User> result = userRepository.findById(user.getId());
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
        userRepository.findById(user.getId()).ifPresent(u -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    public Optional<User> findOne(String userId) {
        return userRepository.findById(userId);
    }
}
