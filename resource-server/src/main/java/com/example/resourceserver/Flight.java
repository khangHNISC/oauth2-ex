package com.example.resourceserver;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by khangld5 on Jul 25, 2022
 */
@Entity
@Getter
@AllArgsConstructor
public class Flight {
    @Id
    private String flightNumber;
    private String pilotId;
    private Status status = Status.BOARD;

    public Flight() {

    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        BOARD, TAXI, TAKE_OFF
    }
}