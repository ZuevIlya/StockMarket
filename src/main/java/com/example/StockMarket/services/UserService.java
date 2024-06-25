package com.example.StockMarket.services;


import com.example.StockMarket.config.WebSecurityConfig;
import com.example.StockMarket.models.User;
import com.example.StockMarket.models.enums.Role;
import com.example.StockMarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean createUser(User user) {
        String username = user.getUsername();
        if (userRepository.findByUsername(username) != null) return false;

        // Хэширование пароля
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String code = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(code);
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        return true;
    }

}