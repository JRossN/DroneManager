package com.drone.delivery.repositories;

import com.drone.delivery.models.Drone;
import com.drone.delivery.models.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    Optional<Drone> findBySerialNumber(String serialNumber);
    boolean existsBySerialNumber(String serialNumber);
    List<Drone> findByStateInAndBatteryCapacityGreaterThanEqual(List<DroneState> states, int batteryCapacity);
} 