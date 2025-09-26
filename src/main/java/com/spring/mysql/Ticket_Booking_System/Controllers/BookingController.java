package com.spring.mysql.Ticket_Booking_System.Controllers;

import com.spring.mysql.Ticket_Booking_System.Entity.Booking;
import com.spring.mysql.Ticket_Booking_System.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    // Book a ticket
    @PostMapping(path = "/add")
    public @ResponseBody Booking bookTicket(@RequestParam Integer userId,
                                            @RequestParam Integer movieId,
                                            @RequestParam Integer numberOfSeats,
                                            @RequestParam String showTime) {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setMovieId(movieId);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setShowTime(showTime);

        booking.setBookingTime(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    @GetMapping(path = "/all")
    public Iterable<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping(path = "/user/{userId}")
    public Iterable<Booking> getBookingsByUser(@PathVariable Integer userId) {
        return bookingRepository.findByUserId(userId);
    }


    @GetMapping("/movie/{movieId}")
    public Iterable<Booking> getBookingsByMovie(@PathVariable Integer movieId) {
        return bookingRepository.findByMovieId(movieId);
    }


    @DeleteMapping(path = "/{id}")
    public String cancelBooking(@PathVariable Integer id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return "Booking cancelled successfully!";
        } else {
            return "Booking not found!";
        }
    }


}

