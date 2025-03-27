package com.drone.delivery.controllers;

import com.drone.delivery.models.Drone;
import com.drone.delivery.models.Medication;
import com.drone.delivery.models.dto.BatteryResponse;
import com.drone.delivery.models.dto.DroneRegistrationRequest;
import com.drone.delivery.models.dto.MedicationLoadRequest;
import com.drone.delivery.services.DroneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drones")
@RequiredArgsConstructor
public class DroneController {
    private final DroneService droneService;

    @PostMapping
    public ResponseEntity<Drone> registerDrone(@Valid @RequestBody DroneRegistrationRequest request) {
        Drone registeredDrone = droneService.registerDrone(request);
        return new ResponseEntity<>(registeredDrone, HttpStatus.CREATED);
    }

    @PostMapping("/{droneId}/load")
    public ResponseEntity<Drone> loadMedications(
            @PathVariable Long droneId,
            @Valid @RequestBody List<MedicationLoadRequest> medications) {
        Drone loadedDrone = droneService.loadMedications(droneId, medications);
        return ResponseEntity.ok(loadedDrone);
    }

    @GetMapping("/{droneId}/medications")
    public ResponseEntity<List<Medication>> getDroneMedications(@PathVariable Long droneId) {
        List<Medication> medications = droneService.getDroneMedications(droneId);
        return ResponseEntity.ok(medications);
    }

    @GetMapping("/{droneId}/battery")
    public ResponseEntity<BatteryResponse> getDroneBattery(@PathVariable Long droneId) {
        int batteryLevel = droneService.getDroneBatteryLevel(droneId);
        return ResponseEntity.ok(new BatteryResponse(batteryLevel));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Drone>> getAvailableDrones() {
        List<Drone> availableDrones = droneService.getAvailableDrones();
        return ResponseEntity.ok(availableDrones);
    }
} 