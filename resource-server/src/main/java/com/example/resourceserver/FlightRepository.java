package com.example.resourceserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by khangld5 on Jul 25, 2022
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
    Flight findByFlightNumber(String flightNumber);
    List<Flight> findByPilotId(String pilotId);
}