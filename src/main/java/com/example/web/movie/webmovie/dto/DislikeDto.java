package com.example.web.movie.webmovie.dto;

public class DislikeDto {
    private Long id;
    private Long userId;
    private String username;

    public DislikeDto() {
    }

    public DislikeDto(Long id, Long userId, String username) {
        this.id = id;
        this.userId = userId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
