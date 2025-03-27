package com.drone.delivery.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationLoadRequest {
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Name can only contain letters, numbers, hyphens, and underscores")
    private String name;

    @NotNull(message = "Weight is required")
    private Integer weight;

    @NotBlank(message = "Code is required")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code can only contain uppercase letters, numbers, and underscores")
    private String code;

    private String imageUrl;

    public MedicationLoadRequest(String name, int weight, String code, String imageUrl) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.imageUrl = imageUrl;
    }
} 