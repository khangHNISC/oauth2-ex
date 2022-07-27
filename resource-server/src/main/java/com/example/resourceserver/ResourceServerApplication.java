package com.example.resourceserver;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    static class FlightDataInitializer implements CommandLineRunner {

        private final FlightRepository flightRepository;

        private final AccessRuleRepository rules;

        @Override
        public void run(String... args) {
            flightRepository.save(new Flight("101", "admin", Flight.Status.TAXI));
            flightRepository.save(new Flight("102", "khang", Flight.Status.BOARD));
            flightRepository.save(new Flight("103", "trong", Flight.Status.BOARD));
            rules.save(new AccessRule("/flights/all", "flights:all"));
            rules.save(new AccessRule("/flights/*/take-off", "flights:approve"));
            rules.save(new AccessRule("/flights", "flights:read"));
            rules.save(new AccessRule("/**", "flights:write"));
        }
    }

}
