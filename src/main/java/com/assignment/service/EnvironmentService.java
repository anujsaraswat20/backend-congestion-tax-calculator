package com.assignment.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConfigurationProperties(prefix = "congestion.tax.agent")
@Getter
@Setter
public class EnvironmentService {
    private List<String> tollfreeVehicles;
    private String adminKey;
    private int firstHour;
    private int secondHour;
    private int firstHourFeeFor0To29Mins;
    private int firstHourFeeFor30To59Mins;
}
