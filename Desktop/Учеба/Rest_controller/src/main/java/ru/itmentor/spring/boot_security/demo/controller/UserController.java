package ru.itmentor.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.ServiceUser;

import java.util.NoSuchElementException;

@RequestMapping("/login/user")
@RestController
public class UserController {


    private final ServiceUser serviceUser;
    @Autowired
    public UserController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }


    @GetMapping()
    public User getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
    User user =null;
        if (userDetails != null) {
         user = serviceUser.getInfo(userDetails);

        if (user == null) {
            throw new NoSuchElementException("user not found");
        }
    }
    return user;
    }

}

