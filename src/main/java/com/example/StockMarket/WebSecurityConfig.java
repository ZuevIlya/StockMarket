package com.example.StockMarket;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    /*
    @Bean
    public static PasswordEncoder getPasswordEncoder() { // Шифрование пароля
        return new BCryptPasswordEncoder(8); // Надёжность ключа шифрования - 8
    }

     */


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/registration", "/welcome").permitAll()
                .requestMatchers("/home/**").authenticated())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);
        return  httpSecurity.build();
    }
}
