package com.spring.mysql.Ticket_Booking_System.Repository;

import com.spring.mysql.Ticket_Booking_System.Entity.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    Iterable<Movie> findByTitleContainingIgnoreCase(String title);
}