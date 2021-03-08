package com.board.boardproject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardDTO {
    private long id;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_date;
    private boolean isDeleted;
    private String writerId;

    public BoardDTO(Board board) {
        this.id = board.getId();
        this.content = board.getContent();
        this.title = board.getTitle();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.create_date = board.getCreate_date();
        this.isDeleted = board.isDeleted();
        this.writerId = board.getWriterId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }
}
