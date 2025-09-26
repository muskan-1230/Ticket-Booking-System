package com.spring.mysql.Ticket_Booking_System.Repository;

import com.spring.mysql.Ticket_Booking_System.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBookingId(Long bookingId);
    List<Payment> findByStatus(String status);
}
