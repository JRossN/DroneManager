package com.drone.delivery.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(max = 100, message = "Serial number cannot exceed 100 characters")
    @NotNull(message = "Serial number is required")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Drone model is required")
    private DroneModel model;

    @Max(value = 1000, message = "Weight limit cannot exceed 1000 grams")
    @NotNull(message = "Weight limit is required")
    private Integer weightLimit;

    @Min(value = 0, message = "Battery capacity cannot be less than 0")
    @Max(value = 100, message = "Battery capacity cannot exceed 100")
    @NotNull(message = "Battery capacity is required")
    private Integer batteryCapacity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Drone state is required")
    private DroneState state;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Medication> medications = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime lastStateChange = LocalDateTime.now();
} 