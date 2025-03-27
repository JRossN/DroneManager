package com.drone.delivery.services;

import com.drone.delivery.exceptions.DroneNotAvailableException;
import com.drone.delivery.models.Drone;
import com.drone.delivery.models.DroneModel;
import com.drone.delivery.models.DroneState;
import com.drone.delivery.models.dto.MedicationLoadRequest;
import com.drone.delivery.repositories.DroneRepository;
import com.drone.delivery.repositories.MedicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private DroneService droneService;

    private Drone testDrone;
    private List<MedicationLoadRequest> medications;

    @BeforeEach
    void setUp() {
        testDrone = new Drone();
        testDrone.setId(1L);
        testDrone.setSerialNumber("TEST001");
        testDrone.setModel(DroneModel.LIGHTWEIGHT);
        testDrone.setWeightLimit(300);
        testDrone.setBatteryCapacity(100);
        testDrone.setState(DroneState.IDLE);

        medications = Arrays.asList(
            new MedicationLoadRequest("Medicine1", 100, "MED001", "image1.jpg"),
            new MedicationLoadRequest("Medicine2", 150, "MED002", "image2.jpg")
        );
    }

    @Test
    void loadMedications_WhenWeightExceedsLimit_ShouldThrowException() {
        // Arrange
        when(droneRepository.findById(1L)).thenReturn(Optional.of(testDrone));
        medications = Arrays.asList(
            new MedicationLoadRequest("Medicine1", 200, "MED001", "image1.jpg"),
            new MedicationLoadRequest("Medicine2", 150, "MED002", "image2.jpg")
        );

        // Act & Assert
        assertThrows(DroneNotAvailableException.class, () -> 
            droneService.loadMedications(1L, medications));
    }

    @Test
    void loadMedications_WhenBatteryLow_ShouldThrowException() {
        // Arrange
        testDrone.setBatteryCapacity(20);
        when(droneRepository.findById(1L)).thenReturn(Optional.of(testDrone));

        // Act & Assert
        assertThrows(DroneNotAvailableException.class, () -> 
            droneService.loadMedications(1L, medications));
    }

    @Test
    void loadMedications_WhenDroneNotFound_ShouldThrowException() {
        // Arrange
        when(droneRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> 
            droneService.loadMedications(1L, medications));
    }

    @Test
    void loadMedications_WhenValid_ShouldUpdateDroneState() {
        // Arrange
        when(droneRepository.findById(1L)).thenReturn(Optional.of(testDrone));
        when(droneRepository.save(any(Drone.class))).thenReturn(testDrone);

        // Act
        Drone result = droneService.loadMedications(1L, medications);

        // Assert
        assertEquals(DroneState.LOADING, result.getState());
        verify(droneRepository).save(any(Drone.class));
    }
} 