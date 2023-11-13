package ru.itmentor.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.ServiceUser;

import java.util.NoSuchElementException;

@RequestMapping("/user")
@RestController
public class UserController {


    private final ServiceUser serviceUser;
    @Autowired
    public UserController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }


    @GetMapping()
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
    User user = null;
    if (userDetails != null) {
        user = serviceUser.getInfo(userDetails);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}

