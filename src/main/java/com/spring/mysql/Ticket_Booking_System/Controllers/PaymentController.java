package com.spring.mysql.Ticket_Booking_System.Controllers;

import com.spring.mysql.Ticket_Booking_System.Entity.Booking;
import com.spring.mysql.Ticket_Booking_System.Entity.Payment;
import com.spring.mysql.Ticket_Booking_System.Repository.BookingRepository;
import com.spring.mysql.Ticket_Booking_System.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private static final double TICKET_PRICE = 250.0; // fixed per seat

    // ============ Make Payment ============
    @PostMapping("/make/{bookingId}")
    public Payment makePayment(@PathVariable Integer bookingId,
                               @RequestParam String paymentMethod) {

        // booking fetch karo
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            throw new RuntimeException("Booking not found for ID: " + bookingId);
        }

        // seats × ticket price
        int seats = booking.getNumberOfSeats();  // 👈 tumhari entity ke hisaab se
        double totalAmount = seats * TICKET_PRICE;


        Payment payment = new Payment();
        payment.setBookingId(booking.getId().longValue()); // Integer → Long convert
        payment.setAmount(totalAmount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("SUCCESS"); // assume success
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    // ============ Get All Payments ============
    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // ============ Get Payment By ID ============
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    // ============ Get Payments By Booking ============
    @GetMapping("/booking/{bookingId}")
    public List<Payment> getPaymentsByBookingId(@PathVariable Long bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    // ============ Get Payments By Status ============
    @GetMapping("/status/{status}")
    public List<Payment> getPaymentsByStatus(@PathVariable String status) {
        return paymentRepository.findByStatus(status);
    }
}
