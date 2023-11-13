package ru.itmentor.spring.boot_security.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.DAO.DaoUserImp;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

@Service
public class ServiceUserImp implements ServiceUser {

private final DaoUserImp daoUserImp;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ServiceUserImp(DaoUserImp daoRoleImp, PasswordEncoder passwordEncoder) {
        this.daoUserImp = daoRoleImp;
        this.passwordEncoder= passwordEncoder;
    }

    @Override
    public void setUserRoles(Long userId, Set<Role> newRoles) {
        daoUserImp.setUserRoles(userId, newRoles);

    }
    public void removeRoles(Long userId, Set<Role> rolesToRemove){
        daoUserImp.removeRoles(userId, rolesToRemove);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return daoUserImp.loadUserByUsername(email);
    }

    @Override
    public void deleteById(Long id) {
        daoUserImp.deleteById(id);
    }

    public User getInfo(UserDetails currentUserDetails){
        return daoUserImp.getInfo(currentUserDetails);
    }

    @Override
    public List<User> listUsers() {
        return daoUserImp.listUsers();
    }

    @Override
    public void saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        daoUserImp.saveUser(user);
    }

    @Override
    public User getUser(Long id) {
        return daoUserImp.getUser(id);
    }

    @Override
    public void updateUser(User user) {
        daoUserImp.updateUser(user);
    }
}
