package com.example.web.movie.webmovie.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CommentDto {
    private Long id;
    private String content;
    private LocalDate date;
    private LocalTime time;
    private Long idUser;
    private String username;
    private String imgUser;

    public CommentDto() {
    }

    public CommentDto(Long id, String content, LocalDate date, LocalTime time, Long idUser, String username, String imgUser) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.time = time;
        this.idUser = idUser;
        this.username = username;
        this.imgUser = imgUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }
}
