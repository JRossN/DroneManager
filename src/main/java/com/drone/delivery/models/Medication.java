package com.drone.delivery.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Name can only contain letters, numbers, hyphens, and underscores")
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Weight is required")
    private Integer weight;

    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code can only contain uppercase letters, numbers, and underscores")
    @NotNull(message = "Code is required")
    private String code;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    @JsonIgnore
    private Drone drone;

    public Medication(String name, int weight, String code, String imageUrl) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.imageUrl = imageUrl;
    }
} 