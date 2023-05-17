package com.example.web.movie.webmovie.dto;

import java.time.LocalDate;

public class LocalStoreDto {
    private Long id;
    private Long movieId;
    private String movieName;
    private String moviePicture;
    private LocalDate releaseDate;
    private double voteAverage;
    private String overview;
    private Long userId;

    public LocalStoreDto() {
    }

    public LocalStoreDto(Long id, Long movieId, String movieName, String moviePicture,LocalDate date, double average, String overview, Long userId) {
        this.id = id;
        this.movieId = movieId;
        this.movieName = movieName;
        this.moviePicture = moviePicture;
        this.releaseDate = date;
        this.voteAverage = average;
        this.overview = overview;
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
