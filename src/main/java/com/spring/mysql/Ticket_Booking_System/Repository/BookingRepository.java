package com.spring.mysql.Ticket_Booking_System.Repository;

import com.spring.mysql.Ticket_Booking_System.Entity.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    Iterable<Booking> findByUserId(Integer userId);
    Iterable<Booking> findByMovieId(Integer movieId);
}
