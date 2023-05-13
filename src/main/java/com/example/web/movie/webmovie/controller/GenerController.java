package com.example.web.movie.webmovie.controller;

import com.example.web.movie.webmovie.exception.ResourceNotFoundException;
import com.example.web.movie.webmovie.model.Gener;
import com.example.web.movie.webmovie.model.Movies;
import com.example.web.movie.webmovie.repository.GenerRepository;
import com.example.web.movie.webmovie.repository.MoviesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class GenerController {

    @Autowired
    GenerRepository generRepository;

    @Autowired
    MoviesRepository moviesRepository;

    // http://localhost:8081/api/geners
    @GetMapping("/geners")
    public ResponseEntity<List<Gener>> getAllGeners() {
        List<Gener> geners = new ArrayList<Gener>();

        generRepository.findAll().forEach(geners::add);

        if(geners.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(geners, HttpStatus.OK);
    }

    // http://localhost:8081/api/movies/{moviesId}/geners
    @GetMapping("/movies/{moviesId}/geners")
    public ResponseEntity<List<Gener>> getAllGenersByMoviesId(@PathVariable(value = "moviesId") Long moviesId) {
        if(!moviesRepository.existsById(moviesId)) {
            throw new ResourceNotFoundException("Not found Movie with id of movie to get all geners= " + moviesId);
        }

        List<Gener> geners = generRepository.findGenersByMoviesId(moviesId);
        return new ResponseEntity<>(geners, HttpStatus.OK);
    }

    @GetMapping("/geners/{id}")
    public ResponseEntity<Gener> getGenersById(@PathVariable(value = "id") Long id) {
        Gener gener = generRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Gener with id of gener= " + id));

        return new ResponseEntity<>(gener, HttpStatus.OK);
    }

    @GetMapping("/geners/{generId}/movies")
    public ResponseEntity<List<Movies>> getAllMoviesByGenerId(@PathVariable(value = "generId") Long generId) {
        Gener gener = generRepository.findById(generId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Gener with id of gener to get all movie= " + generId));

        List<Movies> movies = moviesRepository.findMoviesByGenersId(generId);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PostMapping("/movies/{moviesId}/geners")
    public ResponseEntity<Gener> addTag(@PathVariable(value = "moviesId") Long moviesId, @RequestBody Gener generRequest) {
        Gener gener = moviesRepository.findById(moviesId).map(movies -> {
        	
            long generId = generRequest.getId();
            if(generId != 0L) {
                Gener _gener = generRepository.findById(generId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Gener with id of gener to post for movie= " + generId));
                movies.addGener(_gener);
                moviesRepository.save(movies);
                return _gener;
            }
            movies.addGener(generRequest);
            return generRepository.save(generRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Movies with id of movie of post= " + moviesId));
        return new ResponseEntity<>(gener, HttpStatus.CREATED);
    }

    // http://localhost:8081/api/geners/2
    @PutMapping("/geners/{id}")
    public ResponseEntity<Gener> updateGener(@PathVariable(value = "id") Long id, @RequestBody Gener generRequest) {

        Gener gener = generRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GenerId" + id + "not found"));

        gener.setName(generRequest.getName());
        return new ResponseEntity<>(generRepository.save(gener), HttpStatus.OK);
    }

    // http://localhost:8081/api/movies/2/geners/1
    @DeleteMapping("/movies/{moviesId}/geners/{generId}")
    public ResponseEntity<HttpStatus> deleteGenerFromMovie(@PathVariable(value = "moviesId") Long moviesId, @PathVariable(value = "generId") Long generId){
        Movies movies = moviesRepository.findById(moviesId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found movie with id of movie to delete gener= " + moviesId));

        Gener gener = generRepository.findById(generId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found gener with id of gener to delete gener= " + generId));

        movies.removeGener(generId);
        moviesRepository.save(movies);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // http://localhost:8081/api/geners/2
    @DeleteMapping("/geners/{id}")
    public ResponseEntity<HttpStatus> deleteGener(@PathVariable("id") Long id) {
        List<Movies> movies = moviesRepository.findMoviesByGenersId(id);
        Gener gener = generRepository.findById(id).get();
        for(Movies movie: movies) {
            movie.removeGener(id);
        }
        generRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
