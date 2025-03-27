package com.drone.delivery.models.dto;

import com.drone.delivery.models.DroneModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DroneRegistrationRequest {
    @Size(max = 100, message = "Serial number cannot exceed 100 characters")
    @NotNull(message = "Serial number is required")
    private String serialNumber;

    @NotNull(message = "Drone model is required")
    private DroneModel model;

    @Max(value = 500, message = "Weight limit cannot exceed 500 grams")
    @NotNull(message = "Weight limit is required")
    private Integer weightLimit;

    @Min(value = 0, message = "Battery capacity cannot be less than 0")
    @Max(value = 100, message = "Battery capacity cannot exceed 100")
    @NotNull(message = "Battery capacity is required")
    private Integer batteryCapacity;
} 