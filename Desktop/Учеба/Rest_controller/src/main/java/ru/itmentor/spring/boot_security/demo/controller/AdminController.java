package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<User> getUsers() {
        List<User> users = serviceUser.listUsers();
        return users;
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        serviceUser.saveUser(user);
        serviceUser.setUserRoles(user.getId(), user.getRoles());
        return user;
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        serviceUser.updateUser(user);
        return user;
    }
    @DeleteMapping("/delete/{id}")
    public String deleteUser (@PathVariable("id") Long id){
        Set<Role> rolesToRemove = new HashSet<>();
        serviceUser.removeRoles(id, rolesToRemove);
        serviceUser.deleteById(id);
        return "User with ID =" +id + "delete";
    }

}

