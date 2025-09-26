package com.spring.mysql.Ticket_Booking_System.Controllers;


import com.spring.mysql.Ticket_Booking_System.Entity.User;
import com.spring.mysql.Ticket_Booking_System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(path = "/demo")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // add user
    @PostMapping(path = "/add")
    public @ResponseBody User addNewUser(@RequestParam String name, @RequestParam String email, @RequestParam String phone,
                                         @RequestParam String password){
        User springUser = new User();
        springUser.setName(name);
        springUser.setEmail(email);
        springUser.setPhone(phone);
        springUser.setPassword(password);

        return userRepository.save(springUser);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    //  Update user
    @PutMapping(path = "/{id}")
    public User updateUser(@PathVariable Integer id,
                           @RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String password) {
        User springUser = userRepository.findById(id).orElseThrow();
        springUser.setName(name);
        springUser.setEmail(email);
        springUser.setPhone(phone);
        springUser.setPassword(password);

        return userRepository.save(springUser);
    }

    // Delete user
    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return "User deleted with id: " + id;
    }


}