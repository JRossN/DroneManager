package com.drone.delivery.services;

import com.drone.delivery.config.DroneProperties;
import com.drone.delivery.models.Drone;
import com.drone.delivery.models.DroneState;
import com.drone.delivery.repositories.DroneRepository;
import com.drone.delivery.repositories.MedicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DroneStateService {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DroneProperties droneProperties;

    @Scheduled(fixedRate = 60000) // Run every minute
    @Transactional
    public void updateDroneStates() {
        LocalDateTime now = LocalDateTime.now();
        List<Drone> drones = droneRepository.findAll();

        for (Drone drone : drones) {
            switch (drone.getState()) {
                case LOADING -> handleLoadingState(drone, now);
                case LOADED -> handleLoadedState(drone, now);
                case DELIVERING -> handleDeliveringState(drone, now);
                case DELIVERED -> handleDeliveredState(drone, now);
                case RETURNING -> handleReturningState(drone, now);
                default -> log.debug("Drone {} is in {} state", drone.getId(), drone.getState());
            }
        }
    }

    private void handleLoadingState(Drone drone, LocalDateTime now) {
        if (drone.getLastStateChange().plusMinutes(2).isBefore(now)) {
            drone.setState(DroneState.LOADED);
            drone.setLastStateChange(now);
            droneRepository.save(drone);
            log.info("Drone {} state changed from LOADING to LOADED", drone.getId());
        }
    }

    private void handleLoadedState(Drone drone, LocalDateTime now) {
        if (drone.getLastStateChange().plusMinutes(1).isBefore(now)) {
            drone.setState(DroneState.DELIVERING);
            drone.setLastStateChange(now);
            droneRepository.save(drone);
            log.info("Drone {} state changed from LOADED to DELIVERING", drone.getId());
        }
    }

    private void handleDeliveringState(Drone drone, LocalDateTime now) {
        if (drone.getLastStateChange().plusMinutes(3).isBefore(now)) {
            drone.setState(DroneState.DELIVERED);
            drone.setLastStateChange(now);
            // Clear medications when delivered
            medicationRepository.deleteAll(drone.getMedications());
            drone.getMedications().clear();
            droneRepository.save(drone);
            log.info("Drone {} state changed from DELIVERING to DELIVERED. Medications cleared.", drone.getId());
        }
    }

    private void handleDeliveredState(Drone drone, LocalDateTime now) {
        if (drone.getLastStateChange().plusMinutes(2).isBefore(now)) {
            drone.setState(DroneState.RETURNING);
            drone.setLastStateChange(now);
            droneRepository.save(drone);
            log.info("Drone {} state changed from DELIVERED to RETURNING", drone.getId());
        }
    }

    private void handleReturningState(Drone drone, LocalDateTime now) {
        if (drone.getLastStateChange().plusMinutes(3).isBefore(now)) {
            drone.setState(DroneState.IDLE);
            drone.setLastStateChange(now);
            // Reduce battery level
            int newBatteryLevel = Math.max(0, drone.getBatteryCapacity() - droneProperties.getReductionPerDelivery());
            drone.setBatteryCapacity(newBatteryLevel);
            droneRepository.save(drone);
            log.info("Drone {} state changed from RETURNING to IDLE. New battery level: {}%", 
                    drone.getId(), newBatteryLevel);
        }
    }
} 