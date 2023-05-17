package com.example.web.movie.webmovie.controller;

import com.example.web.movie.webmovie.dto.MovieDto;
import com.example.web.movie.webmovie.exception.ResourceNotFoundException;
import com.example.web.movie.webmovie.model.Movies;
import com.example.web.movie.webmovie.repository.MoviesRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;

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

    // http://localhost:8081/api/movie/stat
    @GetMapping("/movie/stat")
    public ResponseEntity<?> getAllStatOfMovie() {
        List<MovieDto> movieDtos = new ArrayList<>();
                moviesRepository.findAll().forEach(movies -> {
            MovieDto movieDto = new MovieDto();
            movieDto.setId(movies.getId());
            movieDto.setOriginal_title(movies.getOriginal_title());
            movieDto.setLikes(movies.getLikes().stream().count());
            movieDto.setDislikes(movies.getDislikes().stream().count());
            movieDto.setComments(movies.getComments().stream().count());
            movieDtos.add(movieDto);
        });
        return ResponseEntity.ok(movieDtos);
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

    // http://localhost:8081/api/movies/findMovie
    @GetMapping("movies/findMovie")
    public ResponseEntity<?> findMovieFromName(@RequestParam String name) {
        List<Movies> movies = moviesRepository.findNameMovie(name);
        return ResponseEntity.ok(movies);
    }

    // http://localhost:8081/api/movies
    @CrossOrigin
    @PostMapping("/movies")
    public ResponseEntity<Movies> createMovie(@RequestBody Movies movies) {

        Movies _movies = moviesRepository.save(new Movies(movies.getBackdrop_path(), movies.getOriginal_title(), movies.getOverview(), movies.getPoster_path(),
                movies.getRelease_date(), movies.getVote_average(), movies.getVote_count(), movies.getRuntime(), movies.getTagline(), movies.getLink_trailer(),
                movies.getLink_movie()));
        return new ResponseEntity<>(_movies, HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping("movies/{id}")
    public ResponseEntity<Movies> updateMovie(@PathVariable("id") long id, @RequestBody Movies movies) {

        moviesRepository.updateMovie(id,movies.getBackdrop_path(),movies.getOriginal_title(), movies.getOverview(),
                movies.getPoster_path(), movies.getRelease_date(),movies.getVote_average(), movies.getVote_count(),
                movies.getRuntime(), movies.getTagline(), movies.getLink_trailer(), movies.getLink_movie());

        Movies _movies = moviesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Movie with id of movie to update= " + id));
        return ResponseEntity.ok(_movies);
    }

    // http://localhost:8081/api/movies/{id}
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<HttpStatus> deleteMovieId(@PathVariable("id") long id) {
        Movies movies = moviesRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found movie with id of movie to delete= " + id));
        moviesRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
