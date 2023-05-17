package com.example.web.movie.webmovie.dto;

public class MovieDto {
    private Long id;
    private String original_title;
    private Long likes;
    private Long dislikes;
    private Long comments;

    public MovieDto() {
    }

    public MovieDto(Long id, String original_title, Long likes, Long dislikes, Long comments) {
        this.id = id;
        this.original_title = original_title;
        this.likes = likes;
        this.dislikes = dislikes;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getDislikes() {
        return dislikes;
    }

    public void setDislikes(Long dislikes) {
        this.dislikes = dislikes;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }
}
