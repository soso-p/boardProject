package com.board.boardproject.repository;

import com.board.boardproject.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface UserMapper {
    //@Insert("insert into user values (#{user.id}, #{user.password})")
    void save(@Param("user")User user);
    //@Select("select * from user where id = #{id}")
    Optional<User> findById(@Param("id") String id);
    List<User> findAll();
}
