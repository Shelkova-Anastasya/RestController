package ru.itmentor.spring.boot_security.demo.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
public class DaoUserImp implements UserDetailsService {
    private UserDetails currentUserDetails;
    private final PasswordEncoder passwordEncoder;
    private final DaoUser daoUser;

    @Autowired
    public DaoUserImp(DaoUser daoUser, PasswordEncoder passwordEncoder) {
        this.daoUser = daoUser;
        this.passwordEncoder= passwordEncoder;
    }

    public void setUserRoles(Long user_id, Set<Role> newRoles) {
        try {
            User user = daoUser.getOne(user_id);
            user.setRoles(newRoles);
            daoUser.saveAndFlush(user);
        } catch (EntityNotFoundException ex) {
            System.out.println("not found");
        }
    }

    public void removeRoles(Long user_id, Set<Role> rolesToRemove) {
        try {
            User user = daoUser.getOne(user_id);
            Set<Role> userRoles = user.getRoles();
            userRoles.removeAll(rolesToRemove);
            daoUser.saveAndFlush(user);
        } catch (EntityNotFoundException e) {
            System.out.println("User not found");
        }
    }


    @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Попытка загрузить пользователя по email: " + email);
        Optional<User> userOptional = daoUser.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        User user = userOptional.get();
        System.out.println("Пользователь найден: " + user.getEmail());

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
        currentUserDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
        return currentUserDetails;

    }

        public User getInfo(UserDetails currentUserDetails) {

        String loggedInUserEmail = currentUserDetails.getUsername();
        Optional<User> userOptional = daoUser.findByEmail(loggedInUserEmail);
        userOptional.isPresent();
        User user = userOptional.get();


        return user;
         }

        public List<User> listUsers (){
        return daoUser.findAll();
        }

        public void saveUser(User user){
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            daoUser.save(user);
        }


        public User getUser(Long id){
        return daoUser.getOne(id);

        }
        public void updateUser(User user){

        daoUser.saveAndFlush(user);
        }
        public void deleteById(Long id) {
        daoUser.deleteById(id);
    }
}


