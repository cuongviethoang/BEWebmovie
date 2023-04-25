package com.example.web.movie.webmovie.controller;

import com.example.web.movie.webmovie.exception.ResourceNotFoundException;
import com.example.web.movie.webmovie.model.Movies;
import com.example.web.movie.webmovie.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MoviesController {
    @Autowired
    MoviesRepository moviesRepository;



    // http://localhost:8081/api/movies
    @GetMapping("/movies")
    public ResponseEntity<List<Movies>> getAllMovies() {
        List<Movies> movies = new ArrayList<Movies>();
         moviesRepository.findAll().forEach(movies::add);
        if(movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    // http://localhost:8081/api/movie/{id}
    @CrossOrigin
    @GetMapping("/movie/{id}")
    public ResponseEntity<Movies> getMoviesById(@PathVariable("id") long id) {
        Movies movie = moviesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Movie with id of movie to get it= " + id));
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    // http://localhost:8081/api/movies/popular
    @GetMapping("/movies/popular")
    public ResponseEntity<List<Movies>> getAllMoviesPopular() {
        LocalDate now = LocalDate.now();
        LocalDate history = LocalDate.of(2022, 1, 1);
        List<Movies> movies = moviesRepository.findAll();
        List<Movies> moviesPopular = new ArrayList<Movies>();
        for(Movies mv:movies) {
            if(mv.getVote_average() >= 5   && mv.getRelease_date().compareTo(now) < 0 && mv.getRelease_date().compareTo(history) > 0) {
                moviesPopular.add(mv);
            }
        }
        return new ResponseEntity<>(moviesPopular, HttpStatus.OK);
    }

    @GetMapping("/movies/top_rated")
    public ResponseEntity<List<Movies>> getAllMoviesToprated() {
        List<Movies> movies = moviesRepository.findAll();
        List<Movies> moviesToprated = new ArrayList<Movies>();
        for(Movies mv:movies) {
            if(mv.getVote_average() >= 8) {
                moviesToprated.add(mv);
            }
        }
        return new ResponseEntity<>(moviesToprated, HttpStatus.OK);
    }

    @GetMapping("movies/upcoming")
    public ResponseEntity<List<Movies>> getAllMoviesUpcoming() {
        LocalDate now = LocalDate.now();
        List<Movies> movies = moviesRepository.findAll();
        List<Movies> moviesUpcoming = new ArrayList<Movies>();
        for(Movies mv: movies) {
            if(mv.getRelease_date().compareTo(now) > 0) {
                moviesUpcoming.add(mv);
            }
        }
        return new ResponseEntity<>(moviesUpcoming, HttpStatus.OK);
    }

    @PostMapping("/movies")
    public ResponseEntity<Movies> createMovie(@RequestBody Movies movies) {
        Movies _movies = moviesRepository.save(new Movies(movies.getBackdrop_path(), movies.getOriginal_title(), movies.getOverview(), movies.getPoster_path(),
                movies.getRelease_date(), movies.getVote_average(), movies.getVote_count(), movies.getRuntime(), movies.getTagline(), movies.getLink_trailer(), movies.getLink_movie()));
        return new ResponseEntity<>(_movies, HttpStatus.CREATED);
    }

    @PutMapping("movies/{id}")
    public ResponseEntity<Movies> updateMovie(@PathVariable("id") long id, @RequestBody Movies movies) {
        Movies _movies = moviesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Movie with id of movie to update= " + id));

        _movies.setBackdrop_path(movies.getBackdrop_path());
        _movies.setOriginal_title(movies.getOriginal_title());
        _movies.setOverview(movies.getOverview());
        _movies.setPoster_path(movies.getPoster_path());
        _movies.setRelease_date(movies.getRelease_date());
        _movies.setVote_average(movies.getVote_average());
        _movies.setVote_count(movies.getVote_count());
        _movies.setRuntime(movies.getRuntime());
        _movies.setTagline(movies.getTagline());
        _movies.setLink_trailer(movies.getLink_trailer());
        _movies.setLink_movie(movies.getLink_movie());

        return new ResponseEntity<>(moviesRepository.save(_movies), HttpStatus.OK);
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<HttpStatus> deleteMovieId(@PathVariable("id") long id) {
        Movies movies = moviesRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found movie with id of movie to delete= " + id));
        moviesRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("movies")
    public ResponseEntity<HttpStatus> deleteAllMovies() {
        moviesRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
