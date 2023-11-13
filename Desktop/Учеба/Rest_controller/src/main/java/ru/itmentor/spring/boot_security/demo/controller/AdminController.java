package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.ServiceUser;

import java.util.*;



@RequestMapping("/admin")
@RestController
public class AdminController {

    private ServiceUser serviceUser;



    @Autowired
    public AdminController( ServiceUser serviceUser) {
        this.serviceUser = serviceUser;

    }
    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = serviceUser.listUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
    serviceUser.saveUser(user);
    serviceUser.setUserRoles(user.getId(), user.getRoles());
    return new ResponseEntity<>(user, HttpStatus.CREATED);
}

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
    serviceUser.updateUser(user);
    return new ResponseEntity<>(user, HttpStatus.OK);
}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable("id") Long id){
    Set<Role> rolesToRemove = new HashSet<>();
    serviceUser.removeRoles(id, rolesToRemove);
    serviceUser.deleteById(id);
    return new ResponseEntity<>("User with ID =" +id + "delete", HttpStatus.OK);
}


}

