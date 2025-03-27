package com.drone.delivery.services;

import com.drone.delivery.exceptions.DroneNotAvailableException;
import com.drone.delivery.models.Drone;
import com.drone.delivery.models.DroneState;
import com.drone.delivery.models.Medication;
import com.drone.delivery.models.dto.DroneRegistrationRequest;
import com.drone.delivery.models.dto.MedicationLoadRequest;
import com.drone.delivery.repositories.DroneRepository;
import com.drone.delivery.repositories.MedicationRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneService {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    @Transactional
    public Drone registerDrone(DroneRegistrationRequest request) {
        if (droneRepository.existsBySerialNumber(request.getSerialNumber())) {
            throw new EntityExistsException("Drone with serial number " + request.getSerialNumber() + " already exists");
        }

        Drone drone = new Drone();
        drone.setSerialNumber(request.getSerialNumber());
        drone.setModel(request.getModel());
        drone.setWeightLimit(request.getWeightLimit());
        drone.setBatteryCapacity(request.getBatteryCapacity());
        drone.setState(DroneState.IDLE);

        return droneRepository.save(drone);
    }

    @Transactional
    public Drone loadMedications(Long droneId, List<MedicationLoadRequest> medications) {
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new EntityNotFoundException("Drone not found with ID: " + droneId));

        // Validate drone state
        if (drone.getState() != DroneState.IDLE && drone.getState() != DroneState.LOADING) {
            throw new DroneNotAvailableException("Drone is not available for loading. Current state: " + drone.getState());
        }

        // Validate battery level
        if (drone.getBatteryCapacity() < 25) {
            throw new DroneNotAvailableException("Drone battery level is too low: " + drone.getBatteryCapacity() + "%");
        }
        // Calculate total weight including existing medications
        int existingWeight = drone.getMedications().stream()
                .mapToInt(Medication::getWeight)
                .sum();
        
        int newWeight = medications.stream()
                .mapToInt(MedicationLoadRequest::getWeight)
                .sum();
                
        int totalWeight = existingWeight + newWeight;

        // Validate weight limit
        if (totalWeight > drone.getWeightLimit()) {
            throw new DroneNotAvailableException("Total medication weight (" + totalWeight + "g) exceeds drone weight limit (" + drone.getWeightLimit() + "g)");
        }

        // Update drone state
        drone.setState(DroneState.LOADING);
        drone = droneRepository.save(drone);

        // Save medications
        for (MedicationLoadRequest request : medications) {
            Medication medication = new Medication();
            medication.setName(request.getName());
            medication.setWeight(request.getWeight());
            medication.setCode(request.getCode());
            medication.setImageUrl(request.getImageUrl());
            medication.setDrone(drone);
            medicationRepository.save(medication);
        }

        // Update drone state to LOADING
        drone.setState(DroneState.LOADING);
        return droneRepository.save(drone);
    }

    public List<Medication> getDroneMedications(Long droneId) {
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new EntityNotFoundException("Drone not found with ID: " + droneId));
        return drone.getMedications();
    }

    public List<Drone> getAvailableDrones() {
        return droneRepository.findByStateInAndBatteryCapacityGreaterThanEqual(
                List.of(DroneState.IDLE, DroneState.LOADING), 25);
    }

    public int getDroneBatteryLevel(Long droneId) {
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new EntityNotFoundException("Drone not found with ID: " + droneId));
        return drone.getBatteryCapacity();
    }
} 
