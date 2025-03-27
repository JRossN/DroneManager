package com.drone.delivery.controllers;

import com.drone.delivery.models.Drone;
import com.drone.delivery.models.DroneModel;
import com.drone.delivery.models.DroneState;
import com.drone.delivery.models.Medication;
import com.drone.delivery.models.dto.BatteryResponse;
import com.drone.delivery.models.dto.DroneRegistrationRequest;
import com.drone.delivery.models.dto.MedicationLoadRequest;
import com.drone.delivery.services.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DroneControllerTest {

    @Mock
    private DroneService droneService;

    @InjectMocks
    private DroneController droneController;

    private Drone testDrone;
    private List<MedicationLoadRequest> medicationRequests;

    @BeforeEach
    void setUp() {
        testDrone = new Drone();
        testDrone.setId(1L);
        testDrone.setSerialNumber("TEST001");
        testDrone.setModel(DroneModel.LIGHTWEIGHT);
        testDrone.setWeightLimit(300);
        testDrone.setBatteryCapacity(100);
        testDrone.setState(DroneState.IDLE);

        medicationRequests = Arrays.asList(
            new MedicationLoadRequest("Medicine1", 100, "MED001", "image1.jpg"),
            new MedicationLoadRequest("Medicine2", 150, "MED002", "image2.jpg")
        );
    }

    @Test
    void registerDrone_ShouldReturnCreatedStatus() {
        // Arrange
        DroneRegistrationRequest request = new DroneRegistrationRequest();
        request.setSerialNumber("TEST001");
        request.setModel(DroneModel.LIGHTWEIGHT);
        request.setWeightLimit(300);
        request.setBatteryCapacity(100);

        when(droneService.registerDrone(any(DroneRegistrationRequest.class))).thenReturn(testDrone);

        // Act
        ResponseEntity<Drone> response = droneController.registerDrone(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("TEST001", response.getBody().getSerialNumber());
    }

    @Test
    void loadMedications_ShouldReturnOkStatus() {
        // Arrange
        when(droneService.loadMedications(1L, medicationRequests)).thenReturn(testDrone);

        // Act
        ResponseEntity<Drone> response = droneController.loadMedications(1L, medicationRequests);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(DroneState.IDLE, response.getBody().getState());
    }

    @Test
    void getDroneMedications_ShouldReturnOkStatus() {
        // Arrange
        List<Medication> medications = Arrays.asList(
            new Medication("Medicine1", 100, "MED001", "image1.jpg"),
            new Medication("Medicine2", 150, "MED002", "image2.jpg")
        );
        when(droneService.getDroneMedications(1L)).thenReturn(medications);

        // Act
        ResponseEntity<List<Medication>> response = droneController.getDroneMedications(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getDroneBattery_ShouldReturnOkStatus() {
        // Arrange
        when(droneService.getDroneBatteryLevel(1L)).thenReturn(100);

        // Act
        ResponseEntity<BatteryResponse> response = droneController.getDroneBattery(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(100, response.getBody().getBattery());
    }
} 