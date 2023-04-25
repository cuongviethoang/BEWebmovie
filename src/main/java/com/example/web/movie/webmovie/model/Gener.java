package com.example.web.movie.webmovie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "geners")
public class Gener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.MERGE,
                CascadeType.PERSIST,
        },
            mappedBy = "geners"
    )
    @JsonIgnore
    private Set<Movies> movies = new HashSet<>();

    public Gener(){}
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movies> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movies> movies) {
        this.movies = movies;
    }
}
