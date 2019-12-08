package com.example.demo;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

@RestController
class HelloController {

    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/users")
    public List<User> users() {
        return userService.getAll();
    }

    @PostMapping("/users")
    public String users(@RequestBody User user) {
        userService.add(user.getName());
        return "OK";
    }
}

@Service
class UserService {

    @Autowired
    private UserRepository userRepository;

    List<User> getAll() {
        return userRepository.findAll();
    }

    void add(String name){
        userRepository.save(new User(null, name));
    }
}

interface UserRepository extends JpaRepository<User, Long>{

}

@Entity
@Table(name = "boot_user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
