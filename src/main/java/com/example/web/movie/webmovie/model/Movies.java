package com.example.web.movie.webmovie.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "backdrop_path")
    private String backdrop_path;

    @Column(name = "original_title")
    private String original_title;

    @Column(name = "overview")
    private String overview;

    @Column(name = "poster_path")
    private String poster_path;

    @Column(name = "release_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate release_date;

    @Column(name = "vote_average")
    private double vote_average;

    @Column(name = "vote_count")
    private int vote_count;

    @Column(name = "runtime")
    private int runtime;

    @Column(name = "tagline")
    private String tagline;

    @Column(name = "link_trailer")
    private String link_trailer;

    @Column(name = "link_movie")
    private String link_movie;

    @ManyToMany( fetch = FetchType.LAZY,
            cascade = {
                CascadeType.MERGE,
                CascadeType.PERSIST
            })
    @JoinTable(name = "movie_geners",
        joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "gener_id")}
    )
    private Set<Gener> geners = new HashSet<>();

    @OneToMany(mappedBy = "movies", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Comment> comments;

    @OneToMany(mappedBy = "movies", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Like> likes;

    @OneToMany(mappedBy = "movies", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Dislike> dislikes;

    @OneToMany(mappedBy = "movies", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<LocalStore> localStores;



    public Movies(){}

    public Movies(String backdrop_path, String original_title, String overview, String poster_path, LocalDate release_date, double vote_average, int vote_count, int runtime, String tagline, String link_trailer, String link_movie) {

        this.backdrop_path = backdrop_path;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.runtime = runtime;
        this.tagline = tagline;
        this.link_trailer = link_trailer;
        this.link_movie = link_movie;
    }

    public long getId() {
        return id;
    }



    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getLink_trailer() {
        return link_trailer;
    }

    public void setLink_trailer(String link_trailer) {
        this.link_trailer = link_trailer;
    }

    public String getLink_movie() {
        return link_movie;
    }

    public void setLink_movie(String link_movie) {
        this.link_movie = link_movie;
    }

    public Set<Gener> getGeners() {
        return geners;
    }

    public void setGeners(Set<Gener> geners) {
        this.geners = geners;
    }

    @JsonIgnore
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @JsonIgnore
    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    @JsonIgnore
    public Set<Dislike> getDislikes() {
        return dislikes;
    }

    public void setDislikes(Set<Dislike> dislikes) {
        this.dislikes = dislikes;
    }

    @JsonIgnore
    public Set<LocalStore> getLocalStores() {
        return localStores;
    }

    public void setLocalStores(Set<LocalStore> localStores) {
        this.localStores = localStores;
    }

    public void addGener(Gener gener) {
        this.geners.add(gener);
        gener.getMovies().add(this);
    }

    public void removeGener(long generId) {
        Gener gener = this.geners.stream().filter(t -> t.getId() == generId).findFirst().orElse(null);
        if( gener != null) {
            this.geners.remove(gener);
            gener.getMovies().remove(this);
        }
    }




}
