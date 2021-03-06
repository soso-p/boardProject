package com.board.boardproject.repository;

import com.board.boardproject.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface UserRepository {
    void save(@Param("user")User user);
    Optional<User> findById(@Param("id") String id);
    List<User> findAll();
}
