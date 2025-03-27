package com.drone.delivery.services;

import com.drone.delivery.config.DroneProperties;
import com.drone.delivery.models.Drone;
import com.drone.delivery.models.DroneState;
import com.drone.delivery.models.Medication;
import com.drone.delivery.repositories.DroneRepository;
import com.drone.delivery.repositories.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DroneStateServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private DroneProperties droneProperties;

    @InjectMocks
    private DroneStateService droneStateService;

    private Drone testDrone;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        testDrone = new Drone();
        testDrone.setId(1L);
        testDrone.setState(DroneState.LOADING);
        testDrone.setLastStateChange(now.minusMinutes(3)); // More than 2 minutes ago
    }

    @Test
    void updateDroneStates_WhenLoading_ShouldTransitionToLoaded() {
        // Arrange
        when(droneRepository.findAll()).thenReturn(List.of(testDrone));
        when(droneRepository.save(any(Drone.class))).thenReturn(testDrone);

        // Act
        droneStateService.updateDroneStates();

        // Assert
        verify(droneRepository).save(argThat(drone -> 
            drone.getState() == DroneState.LOADED));
    }

    @Test
    void updateDroneStates_WhenLoaded_ShouldTransitionToDelivering() {
        // Arrange
        testDrone.setState(DroneState.LOADED);
        when(droneRepository.findAll()).thenReturn(List.of(testDrone));
        when(droneRepository.save(any(Drone.class))).thenReturn(testDrone);

        // Act
        droneStateService.updateDroneStates();

        // Assert
        verify(droneRepository).save(argThat(drone -> 
            drone.getState() == DroneState.DELIVERING));
    }

    @Test
    void updateDroneStates_WhenDelivering_ShouldTransitionToDeliveredAndClearMedications() {
        // Arrange
        testDrone.setState(DroneState.DELIVERING);
        Medication medication = new Medication();
        medication.setName("Test");
        medication.setWeight(100);
        medication.setCode("TEST001");
        medication.setImageUrl("image.jpg");
        List<Medication> medications = new ArrayList<>();
        medications.add(medication);
        testDrone.setMedications(medications);
        
        when(droneRepository.findAll()).thenReturn(List.of(testDrone));
        when(droneRepository.save(any(Drone.class))).thenReturn(testDrone);

        // Act
        droneStateService.updateDroneStates();

        // Assert
        verify(droneRepository).save(argThat(drone -> 
            drone.getState() == DroneState.DELIVERED));
        verify(medicationRepository).deleteAll(testDrone.getMedications());
    }

    @Test
    void updateDroneStates_WhenDelivered_ShouldTransitionToReturning() {
        // Arrange
        testDrone.setState(DroneState.DELIVERED);
        when(droneRepository.findAll()).thenReturn(List.of(testDrone));
        when(droneRepository.save(any(Drone.class))).thenReturn(testDrone);

        // Act
        droneStateService.updateDroneStates();

        // Assert
        verify(droneRepository).save(argThat(drone -> 
            drone.getState() == DroneState.RETURNING));
    }

    @Test
    void updateDroneStates_WhenReturning_ShouldTransitionToIdleAndReduceBattery() {
        // Arrange
        testDrone.setState(DroneState.RETURNING);
        testDrone.setBatteryCapacity(50);
        testDrone.setLastStateChange(now.minusMinutes(4)); // More than 3 minutes ago
        when(droneRepository.findAll()).thenReturn(List.of(testDrone));
        when(droneRepository.save(any(Drone.class))).thenReturn(testDrone);
        when(droneProperties.getReductionPerDelivery()).thenReturn(10);

        // Act
        droneStateService.updateDroneStates();

        // Assert
        verify(droneRepository).save(argThat(drone -> 
            drone.getState() == DroneState.IDLE && 
            drone.getBatteryCapacity() == 40)); // 50 - 10
    }
} 