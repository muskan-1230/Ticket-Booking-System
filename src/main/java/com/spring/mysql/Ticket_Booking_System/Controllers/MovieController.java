package com.spring.mysql.Ticket_Booking_System.Controllers;


import com.spring.mysql.Ticket_Booking_System.Entity.Movie;
import com.spring.mysql.Ticket_Booking_System.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping(path = "/add")
    public @ResponseBody Movie addNewMovie(@RequestParam String title,
                                           @RequestParam String director,
                                           @RequestParam String duration,
                                           @RequestParam String releaseDate) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDirector(director);
        movie.setDuration(duration);
        movie.setReleaseDate(releaseDate);

        return movieRepository.save(movie);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @PutMapping(path = "/{id}")
    public Movie updateMovie(@PathVariable Integer id,
                             @RequestParam String title,
                             @RequestParam String director,
                             @RequestParam String duration,
                             @RequestParam String releaseDate) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + id));

        movie.setTitle(title);
        movie.setDirector(director);
        movie.setDuration(duration);
        movie.setReleaseDate(releaseDate);

        return movieRepository.save(movie);
    }


    @DeleteMapping(path = "/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie not found with id: " + id);
        }

        movieRepository.deleteById(id);
        return "Movie deleted with id: " + id;
    }


    @GetMapping(path = "/search")
    public Iterable<Movie> searchMovies(@RequestParam String name) {
        return movieRepository.findByTitleContainingIgnoreCase(name);
    }
}
