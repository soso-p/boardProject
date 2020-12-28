package com.board.boardproject.repository;

import com.board.boardproject.domain.Board;
import com.board.boardproject.domain.Paging;
import com.board.boardproject.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class JdbcBoardRepository implements BoardRepository {

    private final DataSource dataSource;

    @Autowired
    public JdbcBoardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 게시글 등록
   @Override
    public Optional<Board> save(Board board) {
        String sql = "insert into board(title, content, create_date, writer_id) value(?, ?, now(), ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setString(3, board.getWriterId());

            pstmt.executeUpdate();

            return Optional.of(board);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    // 게시글 삭제
    @Override
    public Boolean delete(long id) {
        String sql = "update board set isDeleted = 1 where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            pstmt.executeUpdate();

            return true;
        } catch (Exception e) {
            return false;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    // 게시글 수정
    @Override
    public Boolean modify(Board board) {
        String sql = "update board set content = ? where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board.getContent());
            pstmt.setLong(2, board.getId());

            pstmt.executeUpdate();

            return true;
        } catch (Exception e) {
            return false;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Board> findAll() {
        return null;
    }

    @Override
    public int findAllCount() {
        String sql = "select count(*) as count from board where isDeleted = 0";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Board> boardList = new ArrayList<Board>();

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }
            return 0;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    // 게시글 불러오기
    @Override
    public List<Board> findPage(int start, int end) {
        String sql = "select * from board where isDeleted = 0 order by id DESC LIMIT ?, ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Board> boardList = new ArrayList<Board>();

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, start);
            pstmt.setInt(2, end);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setDeleted(rs.getBoolean("isDeleted"));
                board.setCreate_date(rs.getDate("create_date"));
                board.setWriterId(rs.getString("writer_id"));

                boardList.add(board);
            }
            if (boardList.isEmpty()) { return null; }
            return boardList;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public Optional<Board> findByTitle(String title) {
        String sql = "select * from board where isDeleted = 0 and title = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setDeleted(rs.getBoolean("isDeleted"));
                board.setCreate_date(rs.getDate("create_date"));
                board.setWriterId(rs.getString("writer_id"));

                return Optional.of(board);
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws  SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
