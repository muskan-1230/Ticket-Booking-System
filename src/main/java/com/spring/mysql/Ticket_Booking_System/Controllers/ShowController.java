package com.spring.mysql.Ticket_Booking_System.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movie/shows")
public class ShowController {

    @GetMapping(path = "/{movieId}")
    public List<String> getShowTimes(@PathVariable Integer movieId) {

        List<String> showtimes = new ArrayList<>();

        if (movieId == 1) {
            showtimes = List.of("10:00 AM", "1:00 PM", "7:00 PM");
        } else if (movieId == 2) {
            showtimes = List.of("10:00 AM", "1:00 PM", "7:00 PM");
        } else if (movieId == 3) {
            showtimes = List.of("11:00 AM", "3:00 PM", "9:00 PM");
        }else if (movieId == 4) {
            showtimes = List.of("11:00 AM", "3:00 PM", "9:00 PM");
        }else {
            showtimes = List.of("12:00 PM", "4:00 PM", "8:00 PM");
        }

        return showtimes;
    }
}
