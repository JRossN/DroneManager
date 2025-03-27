package com.drone.delivery.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "drone.battery")
public class DroneProperties {
    private int reductionPerDelivery = 10;
} 