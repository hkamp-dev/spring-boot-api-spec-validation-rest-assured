package de.codecentric.springbootapispecvalidationrestassured.configuration;

import de.codecentric.springbootapispecvalidationrestassured.user.User;
import de.codecentric.springbootapispecvalidationrestassured.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> userRepository.save(new User(1L,"testUser", "testUser@email.xyz"));
    }
}
