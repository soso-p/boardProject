package com.board.boardproject;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class mysqlTest {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/board_db?serverTimezone=UTC&characterEncoding=UTF-8"; // jdbc:mysql://127.0.0.1:3306/여러분이 만드신 스키마이름
    private static final String USER = "boardUser"; //DB 사용자명
    private static final String PW = "board1234";   //DB 사용자 비밀번호

    @Test
    public void testConnection() throws Exception{
        Class.forName(DRIVER);

        try(Connection con = DriverManager.getConnection(URL, USER, PW)){
            String sql = "select * from user where id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "test");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("id"));
            }
            System.out.println("성공");
            System.out.println(con);
        }catch (Exception e) {
            System.out.println("에러발생");
            e.printStackTrace();
        }
    }

}