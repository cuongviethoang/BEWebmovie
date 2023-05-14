package com.example.web.movie.webmovie.model;

import com.example.web.movie.webmovie.models.User;

import javax.persistence.*;

@Entity
@Table(name = "dislikereact")
public class Dislike {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movies movies;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Dislike() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movies getMovie() {
        return movies;
    }

    public void setMovie(Movies movies) {
        this.movies = movies;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
