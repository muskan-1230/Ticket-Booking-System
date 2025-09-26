package com.spring.mysql.Ticket_Booking_System.Repository;

import com.spring.mysql.Ticket_Booking_System.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
