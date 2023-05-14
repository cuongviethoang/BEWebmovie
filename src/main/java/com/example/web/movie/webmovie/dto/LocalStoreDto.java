package com.example.web.movie.webmovie.dto;

public class LocalStoreDto {
    private Long id;
    private Long movieId;
    private String movieName;
    private String moviePicture;
    private Long userId;

    public LocalStoreDto() {
    }

    public LocalStoreDto(Long id, Long movieId, String movieName, String moviePicture, Long userId) {
        this.id = id;
        this.movieId = movieId;
        this.movieName = movieName;
        this.moviePicture = moviePicture;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMoviePicture() {
        return moviePicture;
    }

    public void setMoviePicture(String moviePicture) {
        this.moviePicture = moviePicture;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
