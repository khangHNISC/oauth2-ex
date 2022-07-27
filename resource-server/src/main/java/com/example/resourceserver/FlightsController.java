package com.example.resourceserver;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flights")
public class FlightsController {

    private final @NonNull FlightRepository flightRepository;

    @GetMapping("/all")
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @GetMapping
    public List<Flight> getFlights(Authentication authentication) {
        return flightRepository.findByPilotId(authentication.getName());
    }

    @PutMapping("/{flightNumber}/taxi")
    @PostAuthorize("returnObject?.pilotId == authentication.name")
    @Transactional
    public Flight taxi(@PathVariable String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber);
        flight.setStatus(Flight.Status.TAXI);
        return flight;
    }

    @PutMapping("/{flightNumber}/take-off")
    public Flight takeOff(@PathVariable String flightNumber) {
        Flight flight = this.flightRepository.findByFlightNumber(flightNumber);
        flight.setStatus(Flight.Status.TAKE_OFF);
        return flight;
    }
}
