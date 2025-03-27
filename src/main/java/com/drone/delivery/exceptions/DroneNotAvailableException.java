package com.drone.delivery.exceptions;

public class DroneNotAvailableException extends RuntimeException {
    public DroneNotAvailableException(String message) {
        super(message);
    }
} 